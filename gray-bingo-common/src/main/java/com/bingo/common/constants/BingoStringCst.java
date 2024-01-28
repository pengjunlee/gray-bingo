package com.bingo.common.constants;

/**
 * @作者 二月君
 * @版本 1.0
 * @日期 2024-01-21 16:36
 */
public class BingoStringCst {

    public static final String BINGO_LOG_SEPARATOR =
            "=============================================================================================";

    public static final String BINGO_PROP_SEPARATOR =
            ".";

    public static final String BINGO_HELPER_OPEN = "open";

    public static final String BINGO_HELPER_CLOSE = "close";

    public static final String BINGO_HELPER_PREFIX = "bingo.helper";

    public static final String HELPER_NAME_CAFFEINE = "caffeine";
    public static final String HELPER_NAME_DYNAMIC = "dynamic";
    public static final String HELPER_CONF_CAFFEINE = BINGO_HELPER_PREFIX + BINGO_PROP_SEPARATOR + HELPER_NAME_CAFFEINE;

    public static final String HELPER_CONF_DYNAMIC = BINGO_HELPER_PREFIX + BINGO_PROP_SEPARATOR + HELPER_NAME_DYNAMIC;
}
