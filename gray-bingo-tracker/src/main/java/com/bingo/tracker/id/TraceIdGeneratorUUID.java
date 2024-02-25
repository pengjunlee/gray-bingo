package com.bingo.tracker.id;


import com.bingo.common.utils.RandomUtil;

/**
 * trace 生成类
 *
 * @作者 二月君
 * @版本 1.0
 * @日期 2024-01-21 14:17
 */
public class TraceIdGeneratorUUID implements TraceIdGenerator {

    /**
     * 生成 traceId
     *
     * @return traceId
     */
    @Override
    public String traceId() {
        return generator();
    }

    /**
     * 生成 spanId
     *
     * @return spanId
     */
    @Override
    public String spanId() {
        return generator();
    }

    /**
     * 生成ID
     *
     * @return ID
     */
    private String generator() {
        return RandomUtil.uuid().toUpperCase();
    }

}