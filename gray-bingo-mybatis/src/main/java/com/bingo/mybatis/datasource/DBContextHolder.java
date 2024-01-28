package com.bingo.mybatis.datasource;

import java.util.HashMap;
import java.util.Map;


/**
 * 动态数据源上下文
 *
 * @作者 二月君
 * @版本 1.0
 * @日期 2024-01-21 16:15
 */
public class DBContextHolder {

    private static ThreadLocal<String> DB_NAME = new ThreadLocal<>();

    private static ThreadLocal<String> DB_NAME_FIXED = new ThreadLocal<>();

    private static Map<String, String> DB_MAPPING = new HashMap<>();

    /**
     * 通过索引映射数据库
     *
     * @param dbName
     */
    public static void setDBName(String dbName) {
        DB_NAME.set(dbName);
    }

    /**
     * 通过索引映射数据库
     *
     * @param index
     */
    public static void setDBIndex(String index) {
        DB_NAME.set(DB_MAPPING.get(index));
    }

    /**
     * 设置固化数据源
     *
     * @param dbName
     */
    public static void setFixedDBName(String dbName) {
        DB_NAME_FIXED.set(dbName);
    }

    /**
     * 通过索引字段设置固化数据源
     *
     * @param index
     */
    public static void setFixedDBIndex(String index) {
        DB_NAME_FIXED.set(DB_MAPPING.get(index));
    }

    /**
     * 移除固化数据源
     */
    public static void removeFixedDB() {
        DB_NAME_FIXED.remove();
    }

    /**
     * 获取当前索引对应的数据库
     *
     * @return
     */
    public static String getDBName() {
        String dbName = DB_NAME_FIXED.get();
        return dbName == null ? DB_NAME.get() : dbName;
    }

    /**
     * 获取全量映射关系
     *
     * @return
     */
    public static Map<String, String> getDbMapping() {
        return DB_MAPPING;
    }

    /**
     * 添加数据映射关系
     *
     * @param key
     * @param value
     */
    public static void addDbMapping(String key, String value) {
        DB_MAPPING.put(key, value);
    }

}