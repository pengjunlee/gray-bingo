package gray.bingo.tracker.config;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import feign.RequestInterceptor;
import gray.bingo.common.config.condition.ContainPropertyCondition;
import gray.bingo.common.config.condition.KeyValueAnnotation;
import gray.bingo.common.constants.BingoCst;
import gray.bingo.common.constants.DefaultCst;
import gray.bingo.common.constants.SpringCst;
import gray.bingo.common.utils.SpringUtil;
import gray.bingo.common.utils.SystemUtil;
import gray.bingo.tracker.adapter.annotation.TrackerStartAspect;
import gray.bingo.tracker.adapter.async.TrackerTaskDecorator;
import gray.bingo.tracker.adapter.feign.TrackerFeignInterceptor;
import gray.bingo.tracker.adapter.mysql.TrackerMysqlInterceptor;
import gray.bingo.tracker.adapter.spring.TrackerFilter;
import gray.bingo.tracker.collector.TrackerCollector;
import gray.bingo.tracker.collector.TrackerLocalCacheCollector;
import gray.bingo.tracker.common.TrackerCst;
import gray.bingo.tracker.repository.LocalDiskFileRepository;
import gray.bingo.tracker.repository.TrackerRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 链路追踪统一配置入口
 *
 * @作者 Graython
 * @版本 1.0
 * @日期 2024-01-21 14:17
 */
@Slf4j
@Configuration
public class TrackerConfiguration {

    @Bean
    @KeyValueAnnotation(key = BingoCst.CONF_TRACKER_ENABLES, value = SpringCst.ANNOTATION)
    @Conditional(ContainPropertyCondition.class)
    public TrackerStartAspect trackerStartAspect() {
        return new TrackerStartAspect();
    }

    @Bean
    @KeyValueAnnotation(key = BingoCst.CONF_TRACKER_ENABLES, value = SpringCst.SPRING)
    @Conditional(ContainPropertyCondition.class)
    public TrackerFilter trackerFilter() {
        return new TrackerFilter();
    }


    @Bean("trackableExecutor")
    @KeyValueAnnotation(key = BingoCst.CONF_TRACKER_ENABLES, value = SpringCst.ASYNC)
    @Conditional(ContainPropertyCondition.class)
    public Executor trackableExecutor(Environment env) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(SystemUtil.getCpuCores());
        // 最大线程数
        executor.setMaxPoolSize(SystemUtil.getCpuCores());
        // 队列容量
        executor.setQueueCapacity(2048);
        // 当线程空闲时间达到keepAliveTime，该线程会退出，直到线程数量等于corePoolSize。
        // executor.setKeepAliveSeconds(60);
        // 线程名称前缀
        executor.setThreadNamePrefix(env.getProperty(SpringUtil.APP_NAME) + "-trackable-task-");
        // 用来设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 等待时间
        executor.setAwaitTerminationSeconds(60);
        // 增加 TaskDecorator 属性的配置
        executor.setTaskDecorator(new TrackerTaskDecorator(
                "SPRING_ASYNC_TASK", TrackerCst.SPAN_TYPE_SPRING_ASYNC));
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        // 处理未捕获异常日志打印
        final Thread.UncaughtExceptionHandler uncaughtExceptionHandler = (t, e) ->
                log.error("[      BINGO_TRACKERS] >>> 异步线程执行失败。异常信息 => {} : ", e.getMessage(), e);

        ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder();
        threadFactoryBuilder.setNamePrefix(env.getProperty(SpringUtil.APP_NAME) + "-trackable-task-");
        threadFactoryBuilder.setUncaughtExceptionHandler(uncaughtExceptionHandler);
        executor.setThreadFactory(threadFactoryBuilder.build());
        executor.initialize();
        return executor;
    }

    @Bean
    @ConditionalOnClass(value = SqlSession.class)
    @KeyValueAnnotation(key = BingoCst.CONF_TRACKER_ENABLES, value = SpringCst.MYSQL)
    @Conditional(ContainPropertyCondition.class)
    public TrackerMysqlInterceptor trackerMysqlInterceptor() {
        return new TrackerMysqlInterceptor();
    }

    @Bean
    @ConditionalOnClass(value = RequestInterceptor.class)
    @KeyValueAnnotation(key = BingoCst.CONF_TRACKER_ENABLES, value = SpringCst.FEIGN)
    @Conditional(ContainPropertyCondition.class)
    public TrackerFeignInterceptor trackerFeignInterceptor() {
        return new TrackerFeignInterceptor();
    }

    /**
     * havingValue 为一个不存在的值, 永不开启trace收集
     */
    @Bean(name = "trackerRepository")
    @ConditionalOnProperty(value = BingoCst.CONF_TRACKER_REPOSITORY_TYPE, havingValue = BingoCst.VAL_TRACKER_REPOSITORY_TYPE_DISK)
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
    @ConditionalOnProperty(value = BingoCst.CONF_TRACKER_COLLECTOR_ENABLED, havingValue = DefaultCst.TRUE)
    public TrackerCollector traceCollector(TrackerProperties trackerProperties) {
        return new TrackerLocalCacheCollector(trackerProperties);
    }

}
