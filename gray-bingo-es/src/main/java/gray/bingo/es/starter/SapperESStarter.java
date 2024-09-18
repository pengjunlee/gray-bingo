package gray.bingo.es.starter;

import gray.bingo.common.config.BingoHelperFactory;
import gray.bingo.common.config.BingoProp;
import gray.bingo.common.exceptions.BingoException;
import gray.bingo.common.utils.StringUtil;
import gray.bingo.es.config.EsConstant;
import gray.bingo.es.config.EsProperties;
import gray.bingo.es.config.LoadBalancerEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;
import java.util.Map;

/**
 * 描述：es数据源配置
 *
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID REASON   PERSON     DATE
 *  1 Create   高福兵(01409246)   2021/10/14
 * ****************************************************************************
 * </pre>
 *
 * @author 高福兵(01409246)
 * @version 1.0
 */
public class SapperESStarter extends BingoHelperFactory {

    private final Logger logger = LoggerFactory.getLogger(SapperESStarter.class);

    @Override
    public void build(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry registry) throws BeansException {
        logger.info("sapper-elasticsearch-regist begin");
        //  设置默认elasticsearch数据源
        EsPropertiesFactory.setDefaultES(BingoProp.getMap("sapper.elasticsearch").get(EsConstant.DEFAULT));
        //  初始化所有elasticsearch数据源
        BingoProp.getMap("sapper.elasticsearch")
                .keySet()
                .stream()
                .filter(dbName -> !EsConstant.DATASOURCE_UNINITIALIZED.contains(dbName))
                .forEach(dbName -> {
                    logger.info("sapper-elasticsearch-init-{}", dbName);
                    String prefix = "sapper.elasticsearch." + dbName;
                    Map<String, String> esMap = BingoProp.getMap(prefix);
                    String clusterNodes = esMap.get("clusterNodes");
                    String name = esMap.get("name");
                    String rule = esMap.getOrDefault("rule", LoadBalancerEnum.ROUND_ROBIN.getCode());
                    String password = esMap.get("password");
                    String openLog = esMap.getOrDefault("openLog", "false");
                    boolean printLog = Boolean.parseBoolean(openLog);
                    if (StringUtil.isBlank(clusterNodes)) {
                        throw new RuntimeException("es  clusterNodes is null");
                    }
                    EsProperties ESProperties = new EsProperties(name, password, Arrays.asList(clusterNodes.split(",")), rule, printLog);
                    EsPropertiesFactory.addProperties(dbName, ESProperties);
                });
        logger.info("sapper-elasticsearch-regist finished");
    }

    @Override
    public String helperName() {
        return null;
    }

}