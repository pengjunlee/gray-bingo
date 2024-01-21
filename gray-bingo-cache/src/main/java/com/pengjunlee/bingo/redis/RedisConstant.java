package com.pengjunlee.bingo.redis;

import com.pengjunlee.bingo.config.BingoMeta;

/**
 * @author xzzz
 * @since 0.0.1
 */
public final class RedisConstant {

    /**
     * cache 前后缀
     */
    public static final String CACHE_PREFIX_WITH = BingoMeta.META_APPLICATION_NAME + ":";

    public static final String CACHE_SUFFIX_WITH = ":";
}
