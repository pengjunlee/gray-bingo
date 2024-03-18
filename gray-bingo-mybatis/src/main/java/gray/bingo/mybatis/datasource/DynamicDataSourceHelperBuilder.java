package gray.bingo.mybatis.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import gray.bingo.common.config.BingoHelperBuilder;
import gray.bingo.common.config.BingoMeta;
import gray.bingo.common.config.BingoProp;
import gray.bingo.common.constants.BingoCst;
import gray.bingo.common.exceptions.BingoException;
import gray.bingo.common.utils.RSAUtil;
import gray.bingo.common.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 动态数据源初始化
 *
 * @作者 二月の菌
 * @版本 1.0
 * @日期 2024-01-21 16:15
 */
@Slf4j
public class DynamicDataSourceHelperBuilder extends BingoHelperBuilder {

    // 连接池默认配置MAP
    private Map<String, String> defaultMap;

    /**
     * 注册自定义Bean
     */
    @Override
    public void build(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        if (BingoMeta.helperEnabled(BingoCst.BINGO_HELPER_DYNAMIC_DB)) {
            Map<String, String> dbMap = BingoProp.getMap("bingo.db");
            if (ObjectUtils.isEmpty(dbMap)) {
                log.warn("[      HELPER_BUILDER]  -- 注册组件 [ {} ] 失败，原因自定义数据源为空，请检查配置项: [ {} ] ！", helperName(), "bingo.db");
                return;
            }
            //  初始化默认配置
            defaultMap = BingoProp.getMap("bingo.db.default");
            //  过滤出所有的数据库名称
            Map<String, DataSource> customDataSources = this.initDataSource();
            //  注册所有的数据源
            this.registerDataSourceBean(registry, customDataSources);
            //  注册数据源映射关系
            BingoProp.getMap("bingo.mapping.db").forEach((key, value) -> {
                Arrays.stream(value.split(",")).forEach(str -> DBContextHolder.addDbMapping(str, key));
            });
            // 注册慢SQL监控
            BingoProp.getMap("bingo.slow-sql").forEach(DBContextHolder::addDBSlowInterval);
            log.info("[      HELPER_BUILDER]  -- 注册组件 [ {} ] 成功！", helperName());
        } else {
            log.warn("[      HELPER_BUILDER]  -- 注册组件 [ {} ] 失败，原因组件未启用，请检查配置项: [ {} ] ！", helperName(), BingoCst.CONF_HELPER_ENABLES);
        }
    }

    /**
     * 依赖配置文件初始化数据源
     *
     * @return
     */
    private Map<String, DataSource> initDataSource() {
        Map<String, String> rootMap = BingoProp.getMap("bingo.db");
        if (rootMap == null || rootMap.isEmpty()) {
            return null;
        }
        return rootMap.keySet().stream().filter(dbName -> !"default".equals(dbName))
                .collect(Collectors.toMap(dbName -> dbName, this::createDataSource));
    }

    /**
     * 注册动态数据源Bean
     *
     * @param registry          BeanDefinitionRegistry
     * @param customDataSources 自定义数据源
     */
    private void registerDataSourceBean(BeanDefinitionRegistry registry, Map<String, DataSource> customDataSources) {
        // bean定义类
        GenericBeanDefinition define = new GenericBeanDefinition();
        // 设置bean的类型，此处DynamicDataSource继承自AbstractRoutingDataSource
        define.setBeanClass(DynamicDataSource.class);
        // 需要注入的参数
        MutablePropertyValues mpv = define.getPropertyValues();
        // 如果设置了默认数据源，则添加，可以避免key不存在的情况没有数据源可用
        String defaultDBName = defaultMap.get("name");
        if (defaultDBName != null) {
            mpv.add("defaultTargetDataSource", customDataSources.get(defaultDBName));
        }
        // 添加其他数据源
        mpv.add("targetDataSources", customDataSources);
        // 将该bean注册为datasource，不使用spring-boot自动生成的datasource
        registry.registerBeanDefinition("datasource", define);
    }

    /**
     * 创建一个数据源对象
     *
     * @param dbName 数据源名称
     * @return DataSource对象
     */
    private DataSource createDataSource(String dbName) {
        HikariConfig hikariConfig = new HikariConfig();
        // =================== 必选配置 ===================
        // 设置数据库别名
        hikariConfig.setPoolName(dbName);
        // 设置数据库地址
        Map<String, String> dbMap = BingoProp.getMap("bingo.db." + dbName);
        String jdbcURL = StringUtil.isBlank(dbMap.get("url")) ? dbMap.get("jdbcUrl") : dbMap.get("url");
        hikariConfig.setJdbcUrl(jdbcURL);
        // 设置DB库用户名
        hikariConfig.setUsername(dbMap.get("username"));
        // 设置密码
        hikariConfig.setPassword(this.getPassword(dbMap));
        // 驱动配置
        hikariConfig.setDriverClassName(dbMap.get("driver-class-name"));
        // =================== 可选配置 ===================
        // 自动提交事务, 默认值true
        hikariConfig.setAutoCommit(!Boolean.FALSE.equals(getBooleanValue(dbMap, "isAutoCommit")));
        // 最大连接数
        int maxIdle = getIntValue(dbMap, "maxPoolSize");
        hikariConfig.setMaximumPoolSize(maxIdle);
        // 最小连接数, 默认 10 个
        int minIdle = getIntValue(dbMap, "minIdle");
        hikariConfig.setMinimumIdle(minIdle);
        // 从连接池获取连接时最大等待时间, 单位毫秒, 默认值 30秒, 至少 250ms
        hikariConfig.setConnectionTimeout(getLongValue(dbMap, "connectionTimeout"));
        // 连接泄露检测的最大时间, 不能超出这个时间,超出的话就判定为泄露
        hikariConfig.setLeakDetectionThreshold(getLongValue(dbMap, "leakDetectionThreshold"));
        // 连接最大存活时间, 推荐设置的比数据库的 wait_timeout小几分钟
        hikariConfig.setMaxLifetime(getLongValue(dbMap, "maxLifetime"));
        // 连接可以在池中的最大闲置时间, 0 表示永不超时, 该配置不能大于maxLifetime
        hikariConfig.setIdleTimeout(getLongValue(dbMap, "idleTimeout"));
        // 检测连接是否有效的超时时间，不能大于connectionTimeout
        hikariConfig.setValidationTimeout(getLongValue(dbMap, "validationTimeout"));
        return new HikariDataSource(hikariConfig);
    }

    /**
     * 解密密码
     *
     * @param confMap
     */
    private String getPassword(Map<String, String> confMap) {
        try {
            String password = confMap.get("password");
            if (StringUtil.isBlank(password)) return password;
            if (password.startsWith("enc:")) {
                password = RSAUtil.decrypt(password.substring(4));
            }
            return password;
        } catch (Exception e) {
            throw new BingoException(e.getMessage());
        }
    }

    /*****************************工具方法抽离***************************************/
    private Integer getLongValue(Map<String, String> myMap, String key) {
        String longValue = myMap.getOrDefault(myMap.get(key), defaultMap.get(key));
        return longValue == null || longValue.isEmpty() ? null : Integer.parseInt(longValue);
    }

    private Integer getIntValue(Map<String, String> myMap, String key) {
        String intValue = myMap.getOrDefault(myMap.get(key), defaultMap.get(key));
        return intValue == null || intValue.isEmpty() ? null : Integer.parseInt(intValue);
    }

    private Boolean getBooleanValue(Map<String, String> myMap, String key) {
        String booleanValue = myMap.getOrDefault(myMap.get(key), defaultMap.get(key));
        return booleanValue == null || booleanValue.isEmpty() ? null : Boolean.parseBoolean(booleanValue);
    }

    @Override
    public String helperName() {
        return BingoCst.BINGO_HELPER_DYNAMIC_DB;
    }
}