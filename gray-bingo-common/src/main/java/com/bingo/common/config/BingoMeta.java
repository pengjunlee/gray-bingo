package com.bingo.common.config;

import com.bingo.common.Enums.BingoHelperEnum;
import com.bingo.common.Enums.EnabledEnum;
import com.bingo.common.constants.BingoHelperCst;
import com.bingo.common.constants.StringPoolCst;
import com.bingo.common.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;

@Slf4j
public class BingoMeta {

    // 当前服务的部署名称
    public static String META_APPLICATION_NAME;

    // 当前服务部署环境标识
    public static String META_PROFILES_ACTIVE;

    // 当前服务使用的SpringBoot版本
    public static String META_SPRING_BOOT_VER;

    private static Map<String, String> META_HELPER_CONFIGS;

    public static Map<String, String> getMetaHelperConfigs() {
        return META_HELPER_CONFIGS;
    }

    public static void setMetaHelperConfigs(Map<String, String> metaHelperConfigs) {
        BingoMeta.META_HELPER_CONFIGS = metaHelperConfigs;
    }

    public static boolean helperEnabled(String helperPrefix) {
        String helperConfig = META_HELPER_CONFIGS.getOrDefault(helperPrefix, BingoHelperCst.BINGO_HELPER_CLOSE);
        return BingoHelperCst.BINGO_HELPER_OPEN.equals(helperConfig);
    }

    public static void print() {
        log.info("[APPLICATION_LISTENER] >>> 应用名称: {}", META_APPLICATION_NAME);
        log.info("[APPLICATION_LISTENER] >>> 环境标识: {}", META_PROFILES_ACTIVE);
        log.info("[APPLICATION_LISTENER] >>> Springboot版本: {}", META_SPRING_BOOT_VER);
        log.info("[APPLICATION_LISTENER] >>> Helper组件启用状态检查，若需启用或禁用Helper，请参照[ bingo.helper.xxx=open/close ]进行配置 :");
        log.info("[APPLICATION_LISTENER] ┌────────────────┬─────────────────────┬────────┐");
        log.info("[APPLICATION_LISTENER] | ConfigCode     | HelperName          | Status |");
        log.info("[APPLICATION_LISTENER] ├────────────────┼─────────────────────┼────────┤");
        Arrays.stream(BingoHelperEnum.values()).forEach(item -> {
            String helperConfig = META_HELPER_CONFIGS.getOrDefault(item.getCode(), BingoHelperCst.BINGO_HELPER_CLOSE);
            String status = BingoHelperCst.BINGO_HELPER_OPEN.equals(helperConfig) ? EnabledEnum.ENABLED.getName() : EnabledEnum.DISABLED.getName();
            log.info("[APPLICATION_LISTENER] | {}| {}|   {}|", StringUtil.rightFill(item.getCode(), StringPoolCst.C_SPACE, 15), StringUtil.rightFill(item.getName(), StringPoolCst.C_SPACE, 20), StringUtil.rightFill(status, StringPoolCst.C_SPACE, 5));
        });
        log.info("[APPLICATION_LISTENER] └────────────────┴─────────────────────┴────────┘");
    }

    public static void update(String applicationName, String profilesActive, String springBootVersion) {
        META_APPLICATION_NAME = applicationName;
        META_PROFILES_ACTIVE = profilesActive;
        META_SPRING_BOOT_VER = springBootVersion;
    }

}
