package com.bingo.common.config;

import com.bingo.common.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;

import java.util.Collections;
import java.util.Map;

/**
 * Bingo项目的配置
 *
 * @作者 二月君
 * @版本 1.0
 * @日期 2024-01-21 16:52
 */
@Slf4j
public class BingoProp {
    private static Environment environment;

    // 参数绑定工具类，Springboot2.0推出
    private static Binder binder;

    public static void bind(Environment environment) {
        BingoProp.environment = environment;
        BingoProp.binder = Binder.get(BingoProp.environment);
    }

    public static Map<String, String> getMap(String prefix) {
        try {
            return (Map<String, String>) binder.bind(prefix, Map.class).get();
        } catch (Exception e) {
            return Collections.emptyMap();
        }
    }

    public static Integer getInt(Map<String, String> map, String key) {
        String strValue = map.get(key);
        return StringUtil.isBlank(strValue) ? null : Integer.parseInt(strValue);
    }

    public static String getString(Map<String, String> map, String key) {
        String strValue = map.get(key);
        return StringUtil.isBlank(strValue) ? null : strValue;
    }

    public static Boolean getBool(Map<String, String> map, String key) {
        String strValue = map.get(key);
        return StringUtil.isBlank(strValue) ? null : Boolean.parseBoolean(strValue);
    }

    public static <T> T getProperty(String propertyName, Class<T> clazz) {
        return environment.getProperty(propertyName, clazz);
    }

    public static <T> T getProperty(String propertyName, Class<T> clazz, T defaultValue) {
        return environment.getProperty(propertyName, clazz, defaultValue);
    }
}
