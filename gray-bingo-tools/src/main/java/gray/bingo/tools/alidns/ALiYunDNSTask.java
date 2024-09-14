package gray.bingo.tools.alidns;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsRequest;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordRequest;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * 阿里云域名更新任务
 *
 * @author graython
 * @version 1.0
 * @date 2023-08-12 19:15
 */

@AllArgsConstructor
@Slf4j
public class ALiYunDNSTask implements Runnable {

    private String domain;

    private String record;

    private Boolean useIpv6;

    private String accessKey;

    private String secretKey;

    @Override
    public void run() {

        try {
            String ip = getCurrentHostIP(false);
            if (StrUtil.isNotBlank(ip)) {
                doUpdate(ip);
                log.info("[       BINGO_ALI_DNS]  -- 更新 domain=[ {} ],record=[ {} ] 记录IP为IPv4地址: {}", domain, record, ip);
                return;
            }

            if (useIpv6) ip = getCurrentHostIP(true);
            if (StrUtil.isNotBlank(ip)) {
                doUpdate(ip);
                log.info("[       BINGO_ALI_DNS]  -- 更新 domain=[ {} ],record=[ {} ] 记录IP为IPv6地址: {}", domain, record, ip);
            }
        } catch (Exception e) {
            log.error("[       BINGO_ALI_DNS]  -- 更新domain={},record={} 异常: {}", domain, record, ExceptionUtil.getMessage(e));
        }

    }

    public static String getCurrentHostIP(boolean isIPV6) {
        StringBuilder result = new StringBuilder();
        String requestURL = isIPV6 ? ALiYunDNSUpdater.getIPv6Url : ALiYunDNSUpdater.getIPv4Url;

        try {
            // 使用HttpURLConnection网络请求第三方接口
            URL url = new URL(requestURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(60000);
            urlConnection.setReadTimeout(60000);
            urlConnection.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
        } catch (Exception e) {
            log.error("[       BINGO_ALI_DNS]  -- 获取" + (isIPV6 ? "IPV6" : "IPV4") + "地址失败，请检查配置的请求连接和当前网络！");

        }
        return result.toString();

    }

    /**
     * 进行更新操作
     *
     * @param currentValue 当前数值内容
     */
    public void doUpdate(String currentValue) throws ClientException {

        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKey, secretKey);
        IAcsClient client = new DefaultAcsClient(profile);

        DescribeDomainRecordsRequest describeDomainRecordsRequest = new DescribeDomainRecordsRequest();
        describeDomainRecordsRequest.setDomainName(domain);        // 主域名
        describeDomainRecordsRequest.setRRKeyWord(record);  // 主机记录
        describeDomainRecordsRequest.setType("A"); // 解析记录类型

        // 获取主域名的所有解析记录列表
        DescribeDomainRecordsResponse describeDomainRecordsResponse = client.getAcsResponse(describeDomainRecordsRequest);

        // 最新的一条解析记录
        List<DescribeDomainRecordsResponse.Record> domainRecords = describeDomainRecordsResponse.getDomainRecords();

        if (domainRecords == null || domainRecords.size() == 0) {
            log.error("[       BINGO_ALI_DNS]  -- 域名“" + domain + "”下无A记录 “" + record + "” ，请检查阿里云控制台。");

            return;
        }

        DescribeDomainRecordsResponse.Record newestRecord = domainRecords.get(0); //得到最新一条

        String recordID = newestRecord.getRecordId(); //记录RecordID
        String recordValue = newestRecord.getValue();

        if (currentValue.length() > 0 && !currentValue.equals(recordValue)) {
            UpdateDomainRecordRequest updateDomainRecordRequest = new UpdateDomainRecordRequest();
            updateDomainRecordRequest.setRR(record);
            updateDomainRecordRequest.setRecordId(recordID);
            updateDomainRecordRequest.setValue(currentValue);
            updateDomainRecordRequest.setType("A");

            //发出请求，收到回复
            UpdateDomainRecordResponse updateDomainRecordResponse = client.getAcsResponse(updateDomainRecordRequest);

            if (recordID.equals(updateDomainRecordResponse.getRecordId())) {
                log.error("[       BINGO_ALI_DNS]  -- 记录 “" + getFullDomain() + "” 成功更新为 " + currentValue + " 。");
            } else {
                log.error("[       BINGO_ALI_DNS]  -- 记录 “" + getFullDomain() + "” 更新失败,请检查网络与配置。");
            }
        } else {
            log.error("[       BINGO_ALI_DNS]  -- 记录 “" + getFullDomain() + "” 无需更新，跳过。");
        }
    }

    public String getFullDomain() {
        if (record.equals("@")) {
            return domain;
        } else {
            return record + "." + domain;
        }
    }

}
