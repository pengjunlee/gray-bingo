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
        BingoProp.bind(env);
        Map<String, String> bingoHelperConfigs = BingoProp.getMap(BingoStringCst.BINGO_HELPER);
        BingoMeta.setHelperConfigs(bingoHelperConfigs);

    }

    public void check() {
        return;
    }
}
