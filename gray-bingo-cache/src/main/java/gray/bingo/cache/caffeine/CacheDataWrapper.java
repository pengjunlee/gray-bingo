package gray.bingo.cache.caffeine;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * Caffeine存储数据对象
 *
 * @作者 Graython
 * @版本 1.0
 * @日期 2024-01-27 21:01
 */
@Data
@AllArgsConstructor
public class CacheDataWrapper {
    // 缓存内容
    private Object data;
    // 缓存过期时间
    private Long delay;
    // 缓存过期时间单位
    private TimeUnit timeUnit;
}
