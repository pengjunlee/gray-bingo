package gray.bingo.tools.alidns;


import gray.bingo.common.config.BingoHelperFactory;
import gray.bingo.common.config.BingoMeta;
import gray.bingo.common.constants.BingoCst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author 二月の菌
 * @version 1.0
 * @date 2023-08-12 19:02
 */
@Slf4j
public class AliYunDNSHelperFactory extends BingoHelperFactory {
    /**
     * 通过添加如下配置开启组件
     * bingo:
     *  alidns:
     *    domain: javer.love
     *    accessKey: LTAI5tEGk4V7iaPBsoTFmJfU
     *    secretKey: 22NQRy0wND6CypGPUXI0POhk0lF8Lv
     *    records: @,www
     *    ipv6: close
     * @param annotationMetadata
     * @param beanDefinitionRegistry
     */
    @Override
    public void build(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        if (BingoMeta.helperEnabled(BingoCst.BINGO_HELPER_ALI_DNS)) {
            ALiYunDNSUpdater.registerDNSUpdater();
            log.info("[     HELPER_FACTORY]  -- 注册组件 [ {} ] 成功！", helperName());
        }else {
            log.info("[     HELPER_FACTORY]  -- 注册组件 [ {} ] 失败，原因组件未启用，请检查配置项: [ {} ] ！", helperName(), BingoCst.CONF_HELPER_ENABLES);
        }
    }

    @Override
    public String helperName() {
        return BingoCst.BINGO_HELPER_ALI_DNS;
    }

}
