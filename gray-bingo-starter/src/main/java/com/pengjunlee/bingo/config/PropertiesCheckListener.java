package com.pengjunlee.bingo.config;

import com.pengjunlee.bingo.constants.BingoStringCst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;

/**
 * 启动检查配置文件内容, 用于检查配置项是否正确
 */
@Slf4j
public class PropertiesCheckListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment env = event.getEnvironment();

        // 执行配置校验逻辑
        check(env);

        // 绑定Environment对象到BingoProp
        BingoProp.bind(env);

        // 将其中bingo.helper配置绑定到BingoMeta
        Map<String, String> bingoHelperConfigs = BingoProp.getMap(BingoStringCst.BINGO_HELPER_PREFIX);
        BingoMeta.setMetaHelperConfigs(bingoHelperConfigs);

    }

    private void check(ConfigurableEnvironment env) {
        return;
    }
}
