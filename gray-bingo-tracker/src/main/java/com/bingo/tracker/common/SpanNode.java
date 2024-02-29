package com.bingo.tracker.common;

import com.bingo.common.exceptions.BingoException;
import com.bingo.common.utils.JsonUtil;
import com.bingo.common.utils.StringUtil;
import com.bingo.common.utils.TimeUtil;
import com.bingo.tracker.id.TraceIdGeneratorUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.TreeMap;


/**
 * span 对象
 * <p> span 记录了一次追踪中的多个节点，
 *
 * @作者 二月菌
 * @版本 1.0
 * @日期 2024-01-21 16:15
 */
@Data
public final class SpanNode implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 上级 SpanNode
     */
    @JsonIgnore
    private transient SpanNode last;
    /**
     * 下级 SpanNode
     */
    @JsonIgnore
    private transient SpanNode next;
    /**
     * traceId
     */
    private String traceId;
    /**
     * span 名称
     */
    private String spanName;
    /**
     * span 类型
     */
    private String spanType;
    /**
     * spanId
     */
    private String spanId;
    /**
     * spanParentId
     */
    private String spanParentId;
    /**
     * span开始时间
     */
    private long spanStart;
    /**
     * span结束时间
     */
    private long spanEnd;
    /**
     * 用时
     */
    private long spanInterval;
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 自定义说明
     */
    private TreeMap<String, String> records = new TreeMap<>();

    public SpanNode() {
    }

    /**
     * 新建根 span
     *
     * @param spanName
     * @param spanType
     * @return
     */
    public static SpanNode rootSpan(String spanName, String spanType) {
        if (StringUtil.isBlank(spanName)) {
            throw new BingoException("spanName 不能为空");
        }
        SpanNode span = new SpanNode();
        span.setSpanParentId("ROOT_SPAN");
        span.setSpanName(spanName);
        span.setSpanType(spanType);
        span.setTraceId(TraceIdGeneratorUtil.traceId());
        span.setSpanId(TraceIdGeneratorUtil.spanId());
        span.setSpanStart(TimeUtil.currentTimeMillis());
        return span;
    }

    /**
     * 新建子 span
     *
     * @param spanName     span 名称
     * @param spanType
     * @param traceId
     * @param spanParentId
     * @return
     */
    public static SpanNode forkSpan(String spanName, String spanType, final String traceId, final String spanParentId) {
        if (StringUtil.isBlank(spanName)) {
            throw new BingoException("spanName 不能为空");
        }
        if (StringUtil.isBlank(traceId)) {
            throw new BingoException("TraceId 不能为空");
        }
        if (StringUtil.isBlank(spanParentId)) {
            throw new BingoException("spanParentId 不能为空");
        }
        SpanNode span = new SpanNode();
        span.setTraceId(traceId);
        span.setSpanName(spanName);
        span.setSpanType(spanType);
        span.setSpanId(TraceIdGeneratorUtil.spanId());
        span.setSpanParentId(spanParentId);
        span.setSpanStart(TimeUtil.currentTimeMillis());
        return span;
    }

    public boolean hasLast() {
        return null != last;
    }

    public boolean hasNext() {
        return null != next;
    }

    public void setSpanStart(long spanStart) {
        this.spanStart = spanStart;
    }

    public void setSpanEnd(long ignored) {
        this.spanEnd = ignored;
        this.spanInterval = Math.max(this.spanEnd - this.spanStart, 0);
    }

    public void setRecord(String key, String value) {
        this.records.put(key, value);
    }

    public String toJson() {
        return JsonUtil.toJson(this);
    }


}
