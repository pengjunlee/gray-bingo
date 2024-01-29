package com.bingo.common.Enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BingoHelperEnum {

    caffeine("caffeine", "Caffeine Cache","com.bingo.cache.caffeine.CaffeineHelperBuilder"),
    dynamic_db("dynamic_db", "Dynamic DataSource","com.bingo.mybatis.datasource.DynamicDataSourceHelperBuilder"),
    ;

    private String code;

    private String name;

    private String builderClazz;

}
