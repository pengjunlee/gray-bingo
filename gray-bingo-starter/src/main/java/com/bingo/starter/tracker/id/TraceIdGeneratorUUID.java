package com.bingo.starter.tracker.id;


import com.bingo.common.utils.RandomUtil;

/**
 * trace 生成类
 *
 * @author xzzz
 * @since 1.2.0
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
