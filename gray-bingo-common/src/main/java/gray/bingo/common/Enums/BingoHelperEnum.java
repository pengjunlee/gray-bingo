package gray.bingo.common.Enums;

import gray.bingo.common.constants.BingoCst;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BingoHelperEnum {

    CAFFEINE(BingoCst.BINGO_HELPER_CAFFEINE, "Caffeine Cache", "gray.bingo.cache.caffeine.CaffeineHelperFactory"),
    DYNAMIC_DB(BingoCst.BINGO_HELPER_DYNAMIC_DB, "Dynamic DataSource", "gray.bingo.mybatis.datasource.DynamicDataSourceHelperFactory"),
    TRACKER(BingoCst.BINGO_HELPER_TRACKER, "Tracker", null),
    ALI_DNS(BingoCst.BINGO_HELPER_ALI_DNS, "Ali DNS", "gray.bingo.tools.alidns.AliYunDNSHelperFactory"),
    ;

    private final String code;

    private final String name;

    private final String builderClazz;

}
