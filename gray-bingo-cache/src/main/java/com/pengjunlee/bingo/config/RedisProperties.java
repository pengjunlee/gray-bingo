package com.pengjunlee.bingo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * redis 配置
 *
 * @作者 二月君
 * @版本 1.0
 * @日期 2024-01-21 16:15
 */
@Configuration
@ConfigurationProperties(prefix = "bingo.redis")
public class RedisProperties {

}
