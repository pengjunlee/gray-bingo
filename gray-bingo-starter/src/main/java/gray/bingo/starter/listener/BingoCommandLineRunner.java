package gray.bingo.starter.listener;


import gray.bingo.common.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 监听，当前应用程序启动之后，做一些事情
 */
@Component
@Slf4j
public class BingoCommandLineRunner implements CommandLineRunner, Ordered {
    @Override
    public void run(String... args) throws Exception {
        log.info("[  COMMANDLINE_RUNNER] >>> 开始执行 [ {} ]", BingoCommandLineRunner.class);
        log.info("[  COMMANDLINE_RUNNER]  -- args: [ {} ]", StringUtil.join(args, ","));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
