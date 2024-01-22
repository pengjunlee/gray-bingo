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

    private static Map<String, String> META_HELPER_CONFIGS;

    public static Map<String, String> getMetaHelperConfigs() {
        return META_HELPER_CONFIGS;
    }

    public static void setMetaHelperConfigs(Map<String, String> metaHelperConfigs) {
        BingoMeta.META_HELPER_CONFIGS = metaHelperConfigs;
    }

    public static boolean helperEnabled(String helperPrefix) {
        String helperConfig = META_HELPER_CONFIGS.getOrDefault(helperPrefix, BingoStringCst.BINGO_HELPER_CLOSE);
        return BingoStringCst.BINGO_HELPER_OPEN.equals(helperConfig);
    }

    public static String print() {
        Map<String, String> obj = new HashMap<>();
        obj.put("应用名称", META_APPLICATION_NAME);
        obj.put("环境标识", META_PROFILES_ACTIVE);
        obj.put("SpringBoot版本", META_SPRINGBOOT_VER);
        return obj.toString();
    }

    public static void update(String applicationName,String profilesActive,String springBootVersion){
        META_APPLICATION_NAME = applicationName;
        META_PROFILES_ACTIVE = profilesActive;
        META_SPRINGBOOT_VER = springBootVersion;

    }

}
