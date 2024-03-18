package gray.bingo.starter;

import gray.bingo.starter.config.BingoBanner;
import gray.bingo.starter.listener.BingoApplicationListener;
import org.springframework.boot.SpringApplication;

import java.util.Collections;

/**
 * Bingo 应用启动入口
 *
 * @作者 二月の菌
 * @版本 1.0
 * @日期 2024-01-21 14:51
 */
public class BingoStarter {

    public static void run(Class<?> clazz, String[] args) {
        SpringApplication bingoApplication = new SpringApplication(clazz);
        bingoApplication.addPrimarySources(Collections.singleton(BingoStarter.class));
        // ===================== 设置Banner =====================
        // 默认Banner
        bingoApplication.setBanner(new BingoBanner());
        // 关闭Banner
        // bingoApplication.setBannerMode(Banner.Mode.OFF);

        // ===================== 设置Listener =====================
        //
        // 方式一 spring.factories 中配置 org.springframework.context.ApplicationListener
        // 方式二 SpringApplication.addListeners() 方法中添加
        bingoApplication.addListeners(new BingoApplicationListener());
        // 方式三 Spring Bean 自动注册

        // ===================== 设置Initializer =====================
        // 时机 : spring容器还没被刷新之前 准备阶段 this.prepareContext(); 此时所有的配置文件都已经加载
        // 方式一 spring.factories 中配置 org.springframework.context.ApplicationContextInitializer
        // 方式二 SpringApplication.addInitializers() 方法中添加
        // bingoApplication.addInitializers();
        // 方式三 配置文件中配置 context.initializer.classes
        bingoApplication.run(args);
    }
}
