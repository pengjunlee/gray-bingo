package gray.bingo.es.config;

import org.springframework.http.HttpMethod;

import java.lang.annotation.*;

/**
 * 描述：ES 自定义注解
 *
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID REASON   PERSON     DATE
 *  1 Create   高福兵(01409246)   2021/10/18
 * ****************************************************************************
 * </pre>
 *
 * @author 高福兵(01409246)
 * @version 1.0
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EsMapper {

    /**
     * ES 数据源名称
     **/
    String esDbName();

    /**
     * ES 索引
     **/
    String index() default "";

    /**
     * ES type
     **/
    String type() default "";

    /**
     * ES 请求动作（_update、_bulk等），比如getById 没有可不写
     **/
    String action() default "";

    /**
     * 请求方式（参考HttpMethod枚举类）
     **/
    HttpMethod requestType();

}