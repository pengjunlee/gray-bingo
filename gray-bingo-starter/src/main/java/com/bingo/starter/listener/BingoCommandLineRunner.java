package com.bingo.starter.listener;


import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 监听，当前应用程序启动之后，做一些事情
 */
@Component
public class BingoCommandLineRunner implements CommandLineRunner, Ordered {
    @Override
    public void run(String... args) throws Exception {

    }

    @Override
    public int getOrder() {
        return 0;
    }
}
