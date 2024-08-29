package gray.bingo.tools.qiniu;


import gray.bingo.common.config.BingoHelperFactory;
import gray.bingo.common.config.BingoMeta;
import gray.bingo.common.config.BingoProp;
import gray.bingo.common.constants.BingoCst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

@Slf4j
public class QiNiuHelperFactory extends BingoHelperFactory {

    /**
     * 通过添加如下配置开启组件
     * bingo:
     * qiniu:
     * accessKey: g77sGQdG4sRRKsGRgYGxTUcz30Fka0hEWjcxMFHJ
     * secretKey: 8Zuljjcbip5l2KdGqUMW5MIPDtiTgvzneQKo8vxF
     * bucket: gray-blog
     * downloadUrl: http://ryx0pochc.hn-bkt.clouddn.com/
     *
     * @param annotationMetadata
     * @param beanDefinitionRegistry
     */
    public void build(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        if (BingoMeta.helperEnabled(BingoCst.BINGO_HELPER_QI_NIU)) {
            // 读取配置文件
            Map<String, String> qiNiuMap = BingoProp.getMap("bingo.qiniu");
            this.registerQiNiuClient(qiNiuMap);
            log.info("[     HELPER_FACTORY]  -- 注册组件 [ {} ] 成功！", helperName());
        } else {
            log.info("[     HELPER_FACTORY]  -- 注册组件 [ {} ] 失败，原因组件未启用，请检查配置项: [ {} ] ！", helperName(), BingoCst.CONF_HELPER_ENABLES);
        }
    }

    @Override
    public String helperName() {
        return BingoCst.BINGO_HELPER_QI_NIU;
    }


    private void registerQiNiuClient(Map<String, String> qiNiuMap) {
        new GrayQiNiuClient().initAuth(qiNiuMap.get("accessKey"), qiNiuMap.get("secretKey"));
    }
}
