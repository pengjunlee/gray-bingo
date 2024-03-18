package gray.bingo.cache.caffeine;

import com.github.benmanes.caffeine.cache.Cache;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Caffeine操作缓存辅助类
 *
 * @作者 二月の菌
 * @版本 1.0
 * @日期 2024-01-27 21:01
 */
public class CaffeineHelper {

    /**
     * 默认Cache
     */
    private static Cache<String, CacheDataWrapper> cache;

    /**
     * 初始化
     *
     * @param defaultCache
     */
    public void initCache(Cache<String, CacheDataWrapper> defaultCache) {
        CaffeineHelper.cache = defaultCache;
    }

    /**
     * 获取所有key
     *
     * @return
     */
    public static Set<String> getAllKeys() {
        return cache.asMap().keySet();
    }

    /**
     * 获取所有缓存
     *
     * @return
     */
    public static Map<String, CacheDataWrapper> getAllCache() {
        return cache.asMap();
    }

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    public static Boolean exist(final String key) {
        return cache.asMap().containsKey(key);
    }

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     */
    public static void set(final String key, final Object value) {
        try {
            cache.put(key, new CacheDataWrapper(value, 0l, null));
        } catch (Exception e) {
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
            cache.put(key, new CacheDataWrapper(value, expire, timeUnit));
        } catch (Exception e) {
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
            CacheDataWrapper cacheDataWrapper = cache.getIfPresent(key);
            if (Objects.nonNull(cacheDataWrapper)) {
                return cacheDataWrapper.getData();
            }
            return null;
        } catch (Exception e) {
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
            cache.invalidate(key);
        } catch (Exception e) {
            throw new RuntimeException("Caffeine 缓存删除异常:" + e.getMessage());
        }
    }

    public static Cache<String, CacheDataWrapper> load() {
        return cache;
    }

    /**
     * 带缓存获取数据
     *
     * @param prefix
     * @param id
     * @param seconds
     * @param function
     * @param <T>
     * @param <R>
     * @return
     */
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
