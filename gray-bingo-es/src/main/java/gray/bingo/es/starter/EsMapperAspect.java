package gray.bingo.es.starter;

import gray.bingo.common.exceptions.BingoException;
import gray.bingo.common.utils.SpringUtil;
import gray.bingo.common.utils.StringUtil;
import gray.bingo.es.config.*;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ParamNameResolver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.apache.tomcat.util.codec.binary.Base64;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：ES  mybatis aop 拦截
 *
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID REASON   PERSON     DATE
 *  1 Create   高福兵(01409246)   2021/10/18
 * ****************************************************************************
 * </pre>
 *
 * @author 高福兵(01409246)
 * @version 1.0
 */
@Aspect
@Component
public class EsMapperAspect implements EnvironmentAware {


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 配置上下文（也可以理解为配置文件的获取工具）
    private static Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    @Autowired
    private RestTemplate restTemplate;

    @Around(value = "@annotation(esMapper)")
    public Object doAround(ProceedingJoinPoint joinPoint, EsMapper esMapper) throws Throwable {
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Class<?> returnType = method.getReturnType();
        DslConfiguration configuration = SpringUtil.getBean(DslConfiguration.class);
        ParamNameResolver resolver = new ParamNameResolver(configuration, method);
        Object namedParams = resolver.getNamedParams(args);
        String mapperId = method.getDeclaringClass().getName() + "." + method.getName();
        MappedStatement statement = configuration.getMappedStatement(mapperId);
        String dsl = this.getDsl(configuration, statement.getBoundSql(namedParams));
        String dbName = esMapper.esDbName();
        String index = esMapper.index();
        String type = esMapper.type();
        String action = esMapper.action();
        dbName = parserAnnotationParam(dbName);
        index = parserAnnotationParam(index);
        type = parserAnnotationParam(type);
        action = parserAnnotationParam(action);
        HttpMethod httpMethod = esMapper.requestType();
        Map<String, Object> map = (Map) namedParams;
        Object id = map.getOrDefault("id", null);
        EsProperties properties = EsPropertiesFactory.getProperties(dbName);
        if (properties == null) {
            logger.warn("not find properties:{}", dbName);
            return null;
        }
        StringBuilder url = new StringBuilder();
        LoadBalancerRule rule = LoadBalancerRule.getRule(properties.getRule());
        String serviceIp = rule.getServiceId(properties.getClusterNodes());
        url.append("http://").append(serviceIp);
        if (StringUtil.isNotBlank(index)) {
            url.append("/").append(index);
        }
        if (StringUtil.isNotBlank(type)) {
            url.append("/").append(type);
        }

        if (StringUtil.isNotBlank(action)) {
            url.append("/").append(action);
        }
        if (id != null) {
            url.append("/").append(id);
        }
        try {
            if (properties.isOpenLog() && logger.isInfoEnabled()) {
                logger.info("dsl->:{}", dsl);
            }
            return this.sendRequestEs(url.toString(), properties.getName(), properties.getPassword(), dsl, httpMethod, returnType).getBody();
        } catch (Exception e) {
            logger.warn("request es error :{}", e.getMessage());
            throw new BingoException(e.getMessage());
        }
    }

    private String parserAnnotationParam(String field) {
        if (StringUtil.isBlank(field)) {
            return null;
        }
        return environment.resolveRequiredPlaceholders(field);
    }

    /**
     * @return java.lang.String
     * @Author 高福兵(01409246)
     * @Description 进行？的替换
     * @Date 2021/11/3
     * @Param [org.apache.ibatis.session.Configuration, org.apache.ibatis.mapping.BoundSql]
     **/
    private String getDsl(Configuration configuration, BoundSql boundSql) {
        // 获取参数
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        // sql语句中多个空格都用一个空格代替
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (!CollectionUtils.isEmpty(parameterMappings) && parameterObject != null) {
            // 获取类型处理器注册器，类型处理器的功能是进行java类型和数据库类型的转换<br>　
            // 如果根据parameterObject.getClass(）可以找到对应的类型，则替换
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(parameterObject)));
            } else {
                // MetaObject主要是封装了originalObject对象，提供了get和set的方法用于获取和设置originalObject的属性值,主要支持对JavaBean、Collection、Map三种类型对象的操作
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        // 该分支是动态sql
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                    } else {
                        sql = sql.replaceFirst("\\?", "");
                    }
                }
            }
        }
        //处理换行,制表符等特殊字符
        if (StringUtil.isNotBlank(sql)) {
            sql = removeSpecialChars(sql);
            sql = sql.replaceAll("@n", "\n");
        }
        return sql;
    }

    /**
     * @return java.lang.String
     * @Author 高福兵(01409246)
     * @Description 获取参数
     * @Date 2021/11/3
     * @Param [java.lang.Object]
     **/
    private static String getParameterValue(Object obj) {
        String value;
        if (obj instanceof String) {
            //转义值中的双引号
            String val = ((String) obj).replaceAll("\"", "\\\\\"");
            value = "\"" + val + "\"";
        } else if (obj instanceof Date) {
            DateTimeFormatter formatterNew = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
            LocalDateTime ldt = LocalDateTime.ofInstant(((Date) obj).toInstant(), ZoneId.systemDefault());
            value = "\"" + ldt.format(formatterNew) + "\"";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "null";
            }

        }
        return value;
    }


    /**
     * 剔除特殊字段(ASCII码对照表0~31)
     *
     * @return java.lang.String
     * @Author 高福兵(01409246)
     * @Date 2023/7/17
     * @Param [json]
     **/
    private static String removeSpecialChars(String json) {
        Pattern pattern = Pattern.compile(EsConstant.CONTROL_CHAR_REGEX);
        Matcher matcher = pattern.matcher(json);
        return matcher.replaceAll("");
    }

    /**
     * @return java.lang.String
     * @Author 高福兵(01409246)
     * @Description 请求es
     * @Date 2021/11/11
     * @Param [java.lang.String, java.lang.String, java.lang.String, java.lang.String, org.springframework.http.HttpMethod]
     **/
    private <T> ResponseEntity<T> sendRequestEs(String url, String name, String password, String dsl, HttpMethod httpMethod, Class<T> classs) {
        HttpHeaders headers = new HttpHeaders();
        if (StringUtil.isNotBlank(name) && StringUtil.isNotBlank(password)) {
            String authorization = "Basic " + Base64.encodeBase64String((name + ":" + password).getBytes(StandardCharsets.UTF_8));
            headers.set("Authorization", authorization);
        }
        headers.set("Content-Type", "application/json; charset=UTF-8");
        HttpEntity<String> requestEntity = new HttpEntity<>(dsl, headers);
        return restTemplate.exchange(url, httpMethod, requestEntity, classs);
    }
}