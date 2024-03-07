package gray.bingo.common.constants;

/**
 * @作者 二月菌
 * @版本 1.0
 * @日期 2024-01-21 16:36
 */
public class BingoHelperCst {

    public static final String BINGO_HELPER_CONF_NAME = "bingo.helper";
    public static final String BINGO_HELPER_CONF_SEPARATOR = ".";
    public static final String BINGO_HELPER_OPEN = "open";
    public static final String BINGO_HELPER_CLOSE = "close";
    public static final String BINGO_HELPER_CONF_PREFIX = BINGO_HELPER_CONF_NAME + BINGO_HELPER_CONF_SEPARATOR;

    // Helper 名称常量
    public static final String BINGO_HELPER_CAFFEINE = "caffeine";
    public static final String BINGO_HELPER_DYNAMIC_DB = "dynamic_db";

    public static final String BINGO_HELPER_TRACKER = "tracker";


    // Helper 完整配置常量，例如：bingo.helper.caffeine
    public static final String BINGO_HELPER_CONFIG_CAFFEINE = BINGO_HELPER_CONF_PREFIX + BINGO_HELPER_CAFFEINE;

    public static final String BINGO_HELPER_CONFIG_DYNAMIC_DB = BINGO_HELPER_CONF_PREFIX + BINGO_HELPER_DYNAMIC_DB;

    public static final String BINGO_HELPER_CONFIG_TRACKER = BINGO_HELPER_CONF_PREFIX + BINGO_HELPER_TRACKER;


}
