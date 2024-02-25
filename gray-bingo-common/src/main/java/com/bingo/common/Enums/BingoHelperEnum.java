package com.bingo.common.Enums;

import com.bingo.common.constants.BingoHelperCst;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BingoHelperEnum {

    CAFFEINE(BingoHelperCst.BINGO_HELPER_CODE_CAFFEINE, "Caffeine Cache", "com.bingo.cache.caffeine.CaffeineHelperBuilder"),
    DYNAMIC_DB(BingoHelperCst.BINGO_HELPER_CODE_DYNAMIC_DB, "Dynamic DataSource", "com.bingo.mybatis.datasource.DynamicDataSourceHelperBuilder"),
    ;

    private final String code;

    private final String name;

    private final String builderClazz;

}
