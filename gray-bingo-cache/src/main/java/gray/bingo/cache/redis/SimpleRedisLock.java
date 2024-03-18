package gray.bingo.cache.redis;

import gray.bingo.common.config.BingoMeta;
import gray.bingo.common.utils.StringUtil;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 最简单的分布式锁(setnx), 适用于非严格场景.
 * <p>不可重入锁
 * <p>不考虑锁续期
 * <p>锁超时由 redis 自动释放
 *
 * @作者 二月の菌
 * @版本 1.0
 * @日期 2024-01-21 16:15
 */
public class SimpleRedisLock {

    private final String LOCK_PREFIX = ":simple_lock:";
    private final long TIMEOUT = 30 * 1000;
    private final StringRedisTemplate redisTemplate;

    public SimpleRedisLock(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean tryLock(String key) {
        if (StringUtil.isBlank(key)) {
            return false;
        }
        Boolean result = redisTemplate.opsForValue()
                .setIfAbsent(buildKey(key), "lock", TIMEOUT, TimeUnit.MILLISECONDS);
        return result != null && result;
    }

    public boolean release(String key) {
        Boolean result = redisTemplate.delete(buildKey(key));
        return result != null && result;
    }

    private String buildKey(String key) {
        return BingoMeta.META_APPLICATION_NAME + LOCK_PREFIX + key;
    }
}
