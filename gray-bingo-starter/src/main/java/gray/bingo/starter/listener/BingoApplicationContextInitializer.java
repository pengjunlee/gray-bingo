package gray.bingo.starter.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 应用程序初始化之前
 *
 * @作者 graython
 * @版本 1.0
 * @日期 2024-01-21 16:52
 */
@Slf4j
public class BingoApplicationContextInitializer implements ApplicationContextInitializer, Ordered {
    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        log.info("[ CONTEXT_INITIALIZER] >>> 开始执行 [ {} ]", BingoApplicationContextInitializer.class);
        log.info("[ CONTEXT_INITIALIZER] >>> BeanDefinitionCount: [ {} ]", applicationContext.getBeanDefinitionCount());

    }
}
