package com.pengjunlee.bingo;

import com.pengjunlee.bingo.config.BingoBanner;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;

/**
 * @作者 二月君
 * @版本 1.0
 * @日期 2024-01-21 14:51
 */
public class BingoStarter {

    public static void run(Class<?> clazz, String[] args) {
        SpringApplication bingoApplication = new SpringApplication(clazz);
        // 设置默认Banner
        bingoApplication.setBanner(new BingoBanner());
        // 关闭Banner
        // bingoApplication.setBannerMode(Banner.Mode.OFF);
        bingoApplication.run(args);
    }

//    public static void main(String[] args) {
//        run(BingoStarter.class, args);
//    }
}
