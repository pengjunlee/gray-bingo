package com.bingo.starter.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * 从容器获取Bean工具类
 *
 * @作者 二月君
 * @版本 1.0
 * @日期 2024-01-21 15:58
 */
public class BingoContextUtil {

    private static ApplicationContext applicationContext;

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    private static <T> T getBean(String name, Class<T> type) {
        return applicationContext.getBean(name, type);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> baseType) {
        return applicationContext.getBeansOfType(baseType);
    }

    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        applicationContext = ctx;
    }
}
