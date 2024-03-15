package gray.bingo.common.constants;

/**
 * @作者 二月菌
 * @版本 1.0
 * @日期 2024-01-21 16:36
 */
public class BingoCst {

    // =============================== 启用配置 ===============================

    // helper 启用
    public static final String CONF_HELPER_ENABLES = "bingo.helper.enables";

    // tracker 启用
    public static final String CONF_TRACKER_ENABLES = "bingo.tracker.enables";


    // =============================== Helper 名称常量 ===============================
    public static final String BINGO_HELPER_CAFFEINE = "caffeine";
    public static final String BINGO_HELPER_DYNAMIC_DB = "dynamic_db";
    public static final String BINGO_HELPER_TRACKER = "tracker";


    // =============================== Tracker 相关配置 ===============================

    public static final String CONF_TRACKER_PREFIX = "bingo.tracker";
    public static final String CONF_TRACKER_COLLECTOR_ENABLED = "bingo.tracker.collector.enabled";
    public static final String CONF_TRACKER_REPOSITORY_TYPE = "bingo.tracker.repository.type";
    public static final String VAL_TRACKER_REPOSITORY_TYPE_DISK = "disk";
}