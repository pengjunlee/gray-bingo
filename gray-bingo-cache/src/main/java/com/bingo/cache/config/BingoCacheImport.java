package com.bingo.cache.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({BingoCacheProperties.class, CaffeineCacheConfig.class, RedisCacheConfig.class, RedisConfiguration.class})
public class BingoCacheImport {
}
