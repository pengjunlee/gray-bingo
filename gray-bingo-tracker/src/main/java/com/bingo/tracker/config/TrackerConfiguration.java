package com.bingo.tracker.config;
import com.bingo.tracker.adapter.aspect.TrackerStartAspect;
import com.bingo.tracker.adapter.spring.TrackerFilter;
import com.bingo.tracker.collector.TrackerCollector;
import com.bingo.tracker.collector.TrackerLocalCacheCollector;
import com.bingo.tracker.repository.LocalDiskFileRepository;
import com.bingo.tracker.repository.TrackerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 链路追踪配置
 *
 * @作者 二月菌
 * @版本 1.0
 * @日期 2024-01-21 14:17
 */
@Slf4j
@Configuration
public class TrackerConfiguration {

    @Bean
    @ConditionalOnProperty(value = "bingo.tracker.enables.aspect", havingValue = "true")
    public TrackerStartAspect trackerStartAspect() {
        return new TrackerStartAspect();
    }

    @Bean
    @ConditionalOnProperty(value = "bingo.tracker.enables.spring", havingValue = "true")
    public TrackerFilter trackerFilter() {
        return new TrackerFilter();
    }

    /**
     * havingValue 为一个不存在的值, 永不开启trace收集
     */
    @Bean(name = "trackerRepository")
    @ConditionalOnProperty(value = "bingo.tracker.repository.type", havingValue = "disk")
    public TrackerRepository trackerRepository(TrackerProperties trackerProperties) {
        return new LocalDiskFileRepository(trackerProperties);
    }

    /**
     * Tracker span 收集器, 开启收集器才会生效
     *
     * @param trackerProperties 配置
     * @return Tracker span 收集器
     */
    @Bean
    @ConditionalOnBean(name = "trackerRepository")
    @ConditionalOnProperty(value = "bingo.tracker.collector.enabled", havingValue = "true")
    public TrackerCollector traceCollector(TrackerProperties trackerProperties) {
        return new TrackerLocalCacheCollector(trackerProperties);
    }


}
