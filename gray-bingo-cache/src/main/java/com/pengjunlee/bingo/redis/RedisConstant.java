package com.pengjunlee.bingo.redis;

import com.pengjunlee.bingo.utils.StringUtil;

/**
 * @作者 二月君
 * @版本 1.0
 * @日期 2024-01-21 16:15
 */
public final class RedisConstant {

    /**
     * cache 前后缀
     */
    public static String CACHE_PREFIX_WITH = "";

    public static final String CACHE_SUFFIX_WITH = ":";

    public static void refresh(String applicationName){

        CACHE_PREFIX_WITH = StringUtil.isBlank(applicationName)?"":applicationName + ":";
    }
}
