package gray.bingo.common.Enums;

import gray.bingo.common.constants.BingoCst;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BingoHelperEnum {

    CAFFEINE(BingoCst.BINGO_HELPER_CAFFEINE, "Caffeine Cache", "gray.bingo.cache.caffeine.CaffeineHelperBuilder"),
    DYNAMIC_DB(BingoCst.BINGO_HELPER_DYNAMIC_DB, "Dynamic DataSource", "gray.bingo.mybatis.datasource.DynamicDataSourceHelperBuilder"),
    TRACKER(BingoCst.BINGO_HELPER_TRACKER, "Tracker", null),

    ;

    private final String code;

    private final String name;

    private final String builderClazz;

}
