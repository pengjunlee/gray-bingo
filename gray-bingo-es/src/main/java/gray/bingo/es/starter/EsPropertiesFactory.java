package gray.bingo.es.starter;

import gray.bingo.es.config.EsProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * 动态数据源上下文
 */
public class EsPropertiesFactory {

    private EsPropertiesFactory() {}

    private static String DEFAULT_ES;

    private static final Map<String, EsProperties> ES_PROPERTIES_MAPPING = new HashMap<>();

    /**
     * 装载ES属性配置
     * @param ESName ES源名称
     * @param ESProperties ES属性配置
     */
     static void addProperties(String ESName, EsProperties ESProperties){
         ES_PROPERTIES_MAPPING.put(ESName, ESProperties);
    }

    /**
     * 获取默认的ES数据源属性配置
     * @return
     */
     static EsProperties getDefaultProperties(){
        return ES_PROPERTIES_MAPPING.get(DEFAULT_ES);
    }

    /**
     * 通过ES名称获取指定的数据源属性配置
     * @param ESName ES源名称
     * @return
     */
     static EsProperties getProperties(String ESName){
        return ES_PROPERTIES_MAPPING.get(ESName);
    }

    /**
     * 设置默认数据源
     * @param defaultES
     */
     static void setDefaultES(String defaultES) {
        DEFAULT_ES = defaultES;
    }
}