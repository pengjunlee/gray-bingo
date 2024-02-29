package com.bingo.mybatis.datasource;

import java.lang.annotation.*;

/**
 * 默认数据源指定注解
 * <p>指定dbIndex的时候，通过dbIndex映射到实际的数据库</p >
 * <p>dbIndex未配置的时候，使用paramIndex，根据paramIndex配置的顺序获取指定位置的参数来指定默认数据源</p >
 *
 * @作者 二月菌
 * @版本 1.0
 * @日期 2024-01-21 16:15
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface BingoDatasource {

    /**
     * 直接通过数据库名称指定数据库
     */
    String dbName() default "";

    /**
     * 通过数据库索引指定操作的默认数据库
     */
    String dbIndex() default "";

    /**
     * 通过参数序号来指定操作的默认数据库
     */
    int paramIndex() default 1;

}