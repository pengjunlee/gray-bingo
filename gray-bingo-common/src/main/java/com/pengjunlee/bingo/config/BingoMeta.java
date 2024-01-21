package com.pengjunlee.bingo.config;

import com.pengjunlee.bingo.constants.BingoStringCst;

import java.util.HashMap;
import java.util.Map;

public class BingoMeta {

    // 当前服务的部署名称
    public static String META_APPLICATION_NAME;

    // 当前服务部署环境标识
    public static String META_PROFILES_ACTIVE;

    // 当前服务使用的SpringBoot版本
    public static String META_SPRINGBOOT_VER;

    private static Map<String, String> helperConfigs;

    public static Map<String, String> getHelperConfigs() {
        return helperConfigs;
    }

    public static void setHelperConfigs(Map<String, String> helperConfigs) {
        BingoMeta.helperConfigs = helperConfigs;
    }

    public static boolean helperEnabled(String helperPrefix) {
        String helperConfig = helperConfigs.getOrDefault(helperPrefix, BingoStringCst.HELPER_CLOSE);
        return BingoStringCst.HELPER_OPEN.equals(helperConfig);
    }

    public static String print() {
        Map<String, String> obj = new HashMap<>();
        obj.put("应用名称", META_APPLICATION_NAME);
        obj.put("环境标识", META_PROFILES_ACTIVE);
        obj.put("SpringBoot版本", META_SPRINGBOOT_VER);
        return obj.toString();
    }
}
