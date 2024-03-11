package gray.bingo.starter.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 监听，当前应用程序启动之后，做一些事情
 */
@Component
@Slf4j
public class BingoApplicationRunner implements ApplicationRunner, Ordered {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("[  APPLICATION_RUNNER] >>> 开始执行 [ {} ]", BingoApplicationRunner.class);
        log.info("[  APPLICATION_RUNNER]  -- args: [ {} ]", (Object) args.getSourceArgs());
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
