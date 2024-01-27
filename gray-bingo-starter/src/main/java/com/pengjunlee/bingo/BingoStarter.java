package com.pengjunlee.bingo;

import com.pengjunlee.bingo.config.BingoBanner;
import com.pengjunlee.bingo.listener.BingoApplicationListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @作者 二月君
 * @版本 1.0
 * @日期 2024-01-21 14:51
 */

/**
 * 添加 @SpringBootApplication 用于启动测试
 */
@SpringBootApplication(scanBasePackages = {"com.pengjunlee.bingo"})
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

    /**
     * 启动测试入口
     *
     * @param args
     */
    public static void main(String[] args) {
        run(BingoStarter.class, args);
    }
}
