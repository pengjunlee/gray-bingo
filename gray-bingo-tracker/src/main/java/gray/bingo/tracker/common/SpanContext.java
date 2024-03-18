package gray.bingo.tracker.common;


import gray.bingo.common.utils.TimeUtil;
import lombok.Getter;

/**
 * span 的上下文对象，主要负责记录相同线程上下文中的多个 span, 并构造成一个链表
 *
 * @作者 二月の菌
 * @版本 1.0
 * @日期 2024-01-21 16:15
 */
@Getter
public class SpanContext {

    private SpanNode curSpan;

    /**
     * <p> 将新的 span 添加到当前上下文中, 并作为当前 span 记录. 如果在此之前添加过 span, 则多个 span 之间构造成一个双向链表,
     * 新的 span 会添加进链表尾部.
     * <p> 同时, 新添加的的 span 会使用旧 span 的 traceId, 即新 span 不会成为一个新的独立的追踪, 而是并入到当前追踪中.
     *
     * @param currentSpan 返回
     */
    protected void add(final SpanNode currentSpan) {
        if (curSpan == null) {
            curSpan = currentSpan;
            return;
        }

        final SpanNode lastSpan = curSpan;
        lastSpan.setNext(currentSpan);

        currentSpan.setTraceId(lastSpan.getTraceId());
        currentSpan.setSpanParentId(lastSpan.getSpanId());
        currentSpan.setLast(lastSpan);

        this.curSpan = currentSpan;
    }

    /**
     * <p> 删除当前 span, 如果当前 span 存在上一个 span, 删除后将上一个 span 设置为当前 span 并返回
     *
     * @return
     */
    protected SpanNode remove() {
        final SpanNode currentSpan = curSpan;
        currentSpan.setSpanEnd(TimeUtil.currentTimeMillis());
        SpanNode last = currentSpan.getLast();
        if (last != null) {
            last.setNext(null);
            curSpan = last;
        } else {
            curSpan = null;
        }
        return last;
    }
}
