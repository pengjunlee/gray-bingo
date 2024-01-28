package com.bingo.starter.listener;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;

/**
 * 应用程序初始化之前
 */
public class BingoApplicationContextInitializer implements ApplicationContextInitializer, Ordered {
    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

    }
}
