package com.bingo.starter;

import com.bingo.starter.config.BingoBanner;
import com.bingo.starter.listener.BingoApplicationListener;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @作者 二月菌
 * @版本 1.0
 * @日期 2024-01-21 14:51
 */

@ComponentScan(basePackages = "com.bingo")
public class BingoStarter {

    public static void run(Class<?> clazz, String[] args) {
        SpringApplication bingoApplication = new SpringApplication(clazz);
        // 设置默认Banner
        bingoApplication.setBanner(new BingoBanner());
        // 关闭Banner
        // bingoApplication.setBannerMode(Banner.Mode.OFF);
        bingoApplication.addListeners(new BingoApplicationListener());
        bingoApplication.run(args);
    }
}
