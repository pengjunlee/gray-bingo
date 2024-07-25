package gray.bingo.starter.listener;

import gray.bingo.common.config.BingoMeta;
import gray.bingo.common.config.BingoProp;
import gray.bingo.common.constants.BingoCst;
import gray.bingo.common.constants.DefaultCst;
import gray.bingo.common.constants.SpringCst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;


/**
 * 启动时用于检查配置项是否正确
 *
 * @作者 Graython
 * @版本 1.0
 * @日期 2024-01-21 14:51
 */
@Slf4j
public class BingoPropertiesListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent>, Ordered {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        log.info("[APPLICATION_LISTENER] >>> 开始执行 [ {} ]", BingoPropertiesListener.class);
        ConfigurableEnvironment env = event.getEnvironment();

        // 执行配置校验逻辑
        check(env);

        // 绑定Environment对象到BingoProp
        BingoProp.bind(env);

        String applicationName = BingoProp.getProperty(SpringCst.SPRING_APPLICATION_NAME, String.class, DefaultCst.UNKNOWN);
        String profilesActive = BingoProp.getProperty(SpringCst.SPRING_PROFILES_ACTIVE, String.class, DefaultCst.DEFAULT);
        String springBootVersion = SpringBootVersion.getVersion();
        BingoMeta.update(applicationName, profilesActive, springBootVersion);

        // 将其中bingo.helper配置绑定到BingoMeta
        String bingoHelperEnables = BingoProp.getProperty(BingoCst.CONF_HELPER_ENABLES,String.class);
        BingoMeta.setHelperConfigs(bingoHelperEnables);
        BingoMeta.print();
        log.info("[APPLICATION_LISTENER] >>> 执行结束 [ {} ]", BingoPropertiesListener.class);
    }

    private void check(ConfigurableEnvironment env) {
        return;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
