package com.bingo.starter.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 应用程序监听器，监听应用生命周期事件
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class BingoApplicationListener implements ApplicationListener<ApplicationEvent> {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationStartingEvent) {
            log.info("[APPLICATION_LISTENER] >>> 开始启动应用...");
        } else if (event instanceof ApplicationPreparedEvent) {
            log.info("[APPLICATION_LISTENER] >>> 容器准备完毕...");
        } else if (event instanceof ContextClosedEvent) {
            log.info("[APPLICATION_LISTENER] >>> 容器已经关闭...");
        } else if (event instanceof ApplicationFailedEvent) {
            log.info("[APPLICATION_LISTENER] >>> 应用启动失败...");
        }

    }
}
