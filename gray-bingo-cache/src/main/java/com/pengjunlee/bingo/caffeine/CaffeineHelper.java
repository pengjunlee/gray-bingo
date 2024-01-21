package com.pengjunlee.bingo.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class CaffeineHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(CaffeineHelperBuilder.class);

    /**
     * 默认caffeineCache
     */
    private static Cache<String, CacheDataWrapper> defaultCache;

    /**
     * 初始化
     *
     * @param commonCache
     */
    public void initCache(Cache<String, CacheDataWrapper> commonCache) {
        this.defaultCache = commonCache;
    }

    /**
     * 获取所有key
     *
     * @return
     */
    public static Set<String> getAllKeys() {
        return defaultCache.asMap().keySet();
    }

    /**
     * 获取所有缓存
     *
     * @return
     */
    public static Map<String, CacheDataWrapper> getAllCache() {
        return defaultCache.asMap();
    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public static Boolean exist(final String key) {
        return defaultCache.asMap().containsKey(key);
    }

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     */
    public static void set(final String key, final Object value) {
        try {
            defaultCache.put(key, new CacheDataWrapper(value, 0l, null));
        } catch (Exception e) {
            LOGGER.error("Caffeine 缓存写入异常:{}", e);
            throw new RuntimeException("Caffeine 缓存写入异常:" + e.getMessage());
        }
    }

    /**
     * 设置缓存+过期时间
     *
     * @param key
     * @param value
     * @param expire
     * @param timeUnit
     */
    public static void set(final String key, final Object value, final long expire, TimeUnit timeUnit) {
        try {
            defaultCache.put(key, new CacheDataWrapper(value, expire, timeUnit));
        } catch (Exception e) {
            LOGGER.error("Caffeine 缓存写入异常:{}", e);
            throw new RuntimeException("Caffeine 缓存写入异常:" + e.getMessage());
        }
    }

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    public static Object get(String key) {
        try {
            CacheDataWrapper cacheDataWrapper = defaultCache.getIfPresent(key);
            if (Objects.nonNull(cacheDataWrapper)) {
                return cacheDataWrapper.getData();
            }
            return null;
        } catch (Exception e) {
            LOGGER.error("Caffeine 缓存读取异常:{}", e);
            throw new RuntimeException("Caffeine 缓存读取异常:" + e.getMessage());
        }
    }

    /**
     * 删除指定key
     *
     * @param key
     */
    public static void delete(final String key) {
        try {
            defaultCache.invalidate(key);
        } catch (Exception e) {
            LOGGER.error("Caffeine 缓存删除异常:{}", e);
            throw new RuntimeException("Caffeine 缓存删除异常:" + e.getMessage());
        }
    }

    public static Cache<String, CacheDataWrapper> load() {
        return defaultCache;
    }

    public static <T, R> R getWithCache(String prefix, T id, Long seconds, Function<T, R> function) {
        String key = prefix + id;
        Object object = get(key);
        if (Objects.nonNull(object)) return (R) object;
        R result = function.apply(id);
        if (Objects.nonNull(result)) {
            set(key, result, seconds, TimeUnit.SECONDS);
        }
        return result;
    }
}
