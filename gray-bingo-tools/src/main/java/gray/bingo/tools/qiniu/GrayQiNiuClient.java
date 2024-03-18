package gray.bingo.tools.qiniu;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class GrayQiNiuClient {

    private static final long EXPIRE_SECONDS = 60L;
    private static final long F_SIZE_LIMIT = 20971520L;

    /**
     * Auth实例
     */
    private static Auth auth;

    /**
     * 初始化
     */
    public void initAuth(String accessKey, String secretKey) {
        try {
            auth = Auth.create(accessKey, secretKey);
        } catch (Exception e) {
            log.info("[        BINGO_QI_NIU]  -- 七牛云Auth实例化异常:{}", ExceptionUtil.getMessage(e));

        }
    }

    /**
     * 获取上传token
     *
     * @param key
     * @return
     */
    public static String uploadToken(String bucket, String key) {
        return auth.uploadToken(bucket, key);
    }

    public static String uploadLimitToken(String bucket, String key) {
        StringMap putPolicy = new StringMap();
        putPolicy.put("fsizeLimit", F_SIZE_LIMIT);
        return auth.uploadToken(bucket, key, EXPIRE_SECONDS, putPolicy);
    }

    public static void deleteFile(List<String> files, String bucket) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            //单次批量请求的文件数量不得超过1000
            String[] keyList = files.toArray(new String[0]);
            BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
            batchOperations.addDeleteOp(bucket, keyList);
            Response response = bucketManager.batch(batchOperations);
            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
            for (int i = 0; i < keyList.length; i++) {
                BatchStatus status = batchStatusList[i];
                String key = keyList[i];
                if (status.code == 200) {
                    log.info("[        BINGO_QI_NIU]  -- 删除成功 KEY = [ {} ]", key);

                } else {
                    log.info("[        BINGO_QI_NIU]  -- 删除异常 KEY = [ {} ], ERROR = {}", key, status.data.error);

                }
            }
        } catch (QiniuException ex) {
            log.info("[        BINGO_QI_NIU]  -- 删除失败 ERROR = [ {} ]", ex.response.toString());

        }
    }

    public Map<String, Map<String, String>> getFileInfo(List<String> files, String bucket) {
        Map<String, Map<String, String>> result = new HashMap<>();

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            //单次批量请求的文件数量不得超过1000
            String[] keyList = files.toArray(new String[0]);
            BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
            batchOperations.addStatOps(bucket, keyList);
            Response response = bucketManager.batch(batchOperations);
            BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
            for (int i = 0; i < keyList.length; i++) {
                BatchStatus status = batchStatusList[i];
                String key = keyList[i];
                if (status.code == 200) {
                    //文件存在
                    Map<String, String> info = new HashMap<>();
                    info.put("size", String.valueOf(status.data.fsize));
                    info.put("mimeType", status.data.mimeType);
                    result.put(key, info);
                } else {
                    log.info("[        BINGO_QI_NIU]  -- 查询失败 key = [ {} ], ERROR = [ {} ]", key, status.data.error);

                }
            }
        } catch (QiniuException ex) {
            log.info("[        BINGO_QI_NIU]  -- 查询失败 ERROR = [ {} ]", ex.response.toString());
        }

        return result;
    }

}
