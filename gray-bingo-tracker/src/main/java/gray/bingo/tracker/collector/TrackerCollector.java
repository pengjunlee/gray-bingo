package gray.bingo.tracker.collector;


import gray.bingo.tracker.common.SpanNode;

import java.util.List;

/**
 * 追踪收集器
 *
 * @作者 graython
 * @版本 1.0
 * @日期 2024-01-21 14:17
 */
public interface TrackerCollector {

    /**
     * 收集 span 信息
     *
     * @param spanNode span 信息
     */
    void collect(SpanNode spanNode);

    /**
     * 获取 span 列表
     *
     * @return span 列表
     */
    List<SpanNode> get();
}
