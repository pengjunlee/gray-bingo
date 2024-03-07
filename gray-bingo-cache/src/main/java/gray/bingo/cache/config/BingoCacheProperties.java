package gray.bingo.cache.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 缓存配置
 *
 * @作者 二月菌
 * @版本 1.0
 * @日期 2024-01-21 16:15
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "bingo.cache")
public class BingoCacheProperties {

    /**
     * 缓存的 key 配置
     */
    private List<CacheName> cacheNames = new ArrayList<>();

    @Data
    public static class CacheName {
        /**
         * 缓存的 key
         */
        private String name;
        /**
         * 缓存的超时时间, 单位毫秒
         */
        private Integer seconds;
        /**
         * 最大缓存数量, 通常是指 caffeine
         */
        private Integer maximumSize = 1000;
    }
}
