package gray.bingo.tracker.common;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

/**
 * 日志MDC拓展
 *
 * @author graython
 * @since 1.2.0
 */
@Slf4j
public class TrackerMDCLog {

    /**
     * 设置 traceId 到日志 MDC
     * @param traceId traceId
     */
    protected static void traceId(String traceId) {
        try {
            MDC.put(TrackerCst.MDC_TRACE_ID_KEY,traceId);
        } catch (IllegalArgumentException e) {
            log.error("[      BINGO_TRACKERS] >>>  设置日志MDC拓展错误:{}",e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 设置 spanId 到日志 MDC
     * @param spanId spanId
     */
    protected static void spanId(String spanId) {
        try {
            MDC.put(TrackerCst.MDC_SPAN_ID_KEY,spanId);
        } catch (IllegalArgumentException e) {
            log.error("[      BINGO_TRACKERS] >>>  设置日志MDC拓展错误:{}",e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 删除日志MDC
     */
    protected static void removeAll() {
        try {
            MDC.remove(TrackerCst.MDC_TRACE_ID_KEY);
            MDC.remove(TrackerCst.MDC_SPAN_ID_KEY);
        } catch (IllegalArgumentException e) {
            log.error("[      BINGO_TRACKERS] >>>  删除日志MDC拓展错误:{}",e.getMessage());
            e.printStackTrace();
        }
    }
}
