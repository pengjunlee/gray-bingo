package com.pengjunlee.bingo.config;

import com.pengjunlee.bingo.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;

import java.util.Map;

/**
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
            log.error("基于前缀获取配置文件Map失败-{}", prefix);
            throw e;
        }
    }

    public static Integer getInt(Map<String, String> map, String key) {
        String strValue = map.get(key);
        return StringUtil.isEmpty(strValue) ? null : Integer.parseInt(strValue);
    }

    public static String getStr(Map<String, String> map, String key) {
        String strValue = map.get(key);
        return StringUtil.isEmpty(strValue) ? null : strValue;
    }

    public static Boolean getBool(Map<String, String> map, String key) {
        String strValue = map.get(key);
        return StringUtil.isEmpty(strValue) ? null : Boolean.parseBoolean(strValue);
    }

    public static <T> T getProperty(String propertyName, Class<T> clazz) {
        return environment.getProperty(propertyName, clazz);
    }

    public static <T> T getProperty(String propertyName, Class<T> clazz, T defaultValue) {
        return environment.getProperty(propertyName, clazz, defaultValue);
    }
}
