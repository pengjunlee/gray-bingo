package gray.bingo.mybatis.datasource;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;


/**
 * 动态数据源上下文
 *
 * @作者 graython
 * @版本 1.0
 * @日期 2024-01-21 16:15
 */
@Slf4j
public class DBContextHolder {

    private static ThreadLocal<String> DB_NAME = new ThreadLocal<>();

    private static Map<String, Integer> DB_SLOW_INTERVAL = new HashMap<>();

    private static final int DEFAULT_SLOW_INTERVAL = 3000;

    /**
     * 通过索引映射数据库
     *
     * @param dbName
     */
    public static void setDBName(String dbName) {
        DB_NAME.set(dbName);
    }

    public static String getDBName() {
        return DB_NAME.get();
    }

    /**
     * 配置数据源慢SQL超时时间
     * @param dbName
     * @param interval
     */
    public static void addDBSlowInterval(String dbName, int interval) {
        log.info("[     HELPER_FACTORY]  -- 启用慢SQL监控，数据源=[ {} ] 超时时间= [ {} ms ]", dbName,interval);
        DB_SLOW_INTERVAL.put(dbName, interval);
    }

    public static boolean slowSql(long time) {
        if (!DB_SLOW_INTERVAL.containsKey(getDBName())) return false;
        return time > DB_SLOW_INTERVAL.getOrDefault(getDBName(), DEFAULT_SLOW_INTERVAL);
    }

}