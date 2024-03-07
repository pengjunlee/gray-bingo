package gray.bingo.starter.listener;

import gray.bingo.common.config.BingoMeta;
import gray.bingo.common.config.BingoProp;
import gray.bingo.common.constants.BingoHelperCst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;


/**
 * 启动检查配置文件内容, 用于检查配置项是否正确
 *
 * @作者 二月菌
 * @版本 1.0
 * @日期 2024-01-21 14:51
 */
@Slf4j
public class PropertiesCheckListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent>, Ordered {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        log.info("[APPLICATION_LISTENER] >>> 开始执行 [ {} ]", PropertiesCheckListener.class);
        ConfigurableEnvironment env = event.getEnvironment();

        // 执行配置校验逻辑
        check(env);

        // 绑定Environment对象到BingoProp
        BingoProp.bind(env);
        String applicationName = BingoProp.getProperty("spring.application.name", String.class, "服务名称未配置");
        String profilesActive = BingoProp.getProperty("spring.profiles.active", String.class, "default");
        String springbootVersion = SpringBootVersion.getVersion();
        BingoMeta.update(applicationName, profilesActive, springbootVersion);

        // 将其中bingo.helper配置绑定到BingoMeta
        Map<String, String> bingoHelperConfigs = BingoProp.getMap(BingoHelperCst.BINGO_HELPER_CONF_NAME);
        BingoMeta.setMetaHelperConfigs(bingoHelperConfigs);
        BingoMeta.print();
        log.info("[APPLICATION_LISTENER] >>> 执行结束 [ {} ]", PropertiesCheckListener.class);
    }

    private void check(ConfigurableEnvironment env) {
        return;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
