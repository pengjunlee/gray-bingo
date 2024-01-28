package com.bingo.mybatis.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


/**
 * 自定义数据源路由规则
 *
 * @作者 二月君
 * @版本 1.0
 * @日期 2024-01-21 16:15
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DBContextHolder.getDBName();
    }

}