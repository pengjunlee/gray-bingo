package gray.bingo.common.Enums;

import gray.bingo.common.constants.BingoHelperCst;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BingoHelperEnum {

    CAFFEINE(BingoHelperCst.BINGO_HELPER_CAFFEINE, "Caffeine Cache", "gray.bingo.cache.caffeine.CaffeineHelperBuilder"),
    DYNAMIC_DB(BingoHelperCst.BINGO_HELPER_DYNAMIC_DB, "Dynamic DataSource", "gray.bingo.mybatis.datasource.DynamicDataSourceHelperBuilder"),
    TRACKER(BingoHelperCst.BINGO_HELPER_TRACKER, "Tracker", null),

    ;

    private final String code;

    private final String name;

    private final String builderClazz;

}
