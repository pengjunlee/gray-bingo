package gray.bingo.common.config;

import gray.bingo.common.Enums.BingoHelperEnum;
import gray.bingo.common.Enums.EnabledEnum;
import gray.bingo.common.constants.CharPoolCst;
import gray.bingo.common.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class BingoMeta {

    // 当前服务的部署名称
    public static String META_APPLICATION_NAME;

    // 当前服务部署环境标识
    public static String META_PROFILES_ACTIVE;

    // 当前服务使用的SpringBoot版本
    public static String META_SPRING_BOOT_VER;

    private static final Set<String> META_HELPER_ENABLES = new HashSet<>();

    public static void setMetaHelperConfigs(String bingoHelperEnables) {
        if (StringUtil.isNotBlank(bingoHelperEnables)) {
            String[] helperNames = bingoHelperEnables.split(",");
            META_HELPER_ENABLES.addAll(Arrays.asList(helperNames));
        }
    }

    public static boolean helperEnabled(String helperName) {
        return META_HELPER_ENABLES.contains(helperName);
    }

    public static void print() {
        log.info("[APPLICATION_LISTENER] >>> 应用名称: {}", META_APPLICATION_NAME);
        log.info("[APPLICATION_LISTENER] >>> 环境标识: {}", META_PROFILES_ACTIVE);
        log.info("[APPLICATION_LISTENER] >>> SpringBoot版本: {}", META_SPRING_BOOT_VER);
        log.info("[APPLICATION_LISTENER] >>> Helper组件启用状态检查，若需启用或禁用Helper，请参照[ bingo.helper.enables ]进行配置 :");
        log.info("[APPLICATION_LISTENER] ┌────────────────┬─────────────────────┬────────┐");
        log.info("[APPLICATION_LISTENER] | ConfigCode     | HelperName          | Status |");
        log.info("[APPLICATION_LISTENER] ├────────────────┼─────────────────────┼────────┤");
        Arrays.stream(BingoHelperEnum.values()).forEach(item -> {
            String status = helperEnabled(item.getCode()) ? EnabledEnum.ENABLED.getName() : EnabledEnum.DISABLED.getName();
            log.info("[APPLICATION_LISTENER] | {}| {}|   {}|", StringUtil.rightFill(item.getCode(), CharPoolCst.C_SPACE, 15), StringUtil.rightFill(item.getName(), CharPoolCst.C_SPACE, 20), StringUtil.rightFill(status, CharPoolCst.C_SPACE, 5));
        });
        log.info("[APPLICATION_LISTENER] └────────────────┴─────────────────────┴────────┘");
    }

    public static void update(String applicationName, String profilesActive, String springBootVersion) {
        META_APPLICATION_NAME = applicationName;
        META_PROFILES_ACTIVE = profilesActive;
        META_SPRING_BOOT_VER = springBootVersion;
    }

}
