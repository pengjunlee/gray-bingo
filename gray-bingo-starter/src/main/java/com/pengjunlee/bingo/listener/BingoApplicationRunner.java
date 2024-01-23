package com.pengjunlee.bingo.listener;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 监听，当前应用程序启动之后，做一些事情
 */
@Component
public class BingoApplicationRunner implements ApplicationRunner, Ordered {
    @Override
    public void run(ApplicationArguments args) throws Exception {

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
