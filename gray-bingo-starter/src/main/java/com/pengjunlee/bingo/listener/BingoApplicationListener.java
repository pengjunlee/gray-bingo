package com.pengjunlee.bingo.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.core.Ordered;

/**
 * 应用程序监听器，监听应用生命周期事件
 */
@Slf4j
public class BingoApplicationListener implements ApplicationListener<ApplicationEvent>, Ordered {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationStartingEvent) {
            log.info("开始启动应用");
        } else if (event instanceof ApplicationEnvironmentPreparedEvent) {
            log.info("配置加载完毕");
        } else if (event instanceof ApplicationPreparedEvent) {
            log.info("容器准备完毕");
        } else if (event instanceof ContextClosedEvent) {
            log.info("容器已关闭");
        } else if (event instanceof ApplicationFailedEvent) {
            log.info("应用启动失败");
        }

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
