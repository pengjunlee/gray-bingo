package gray.bingo.tracker.id;

/**
 * ID生成接口
 *
 * @作者 二月菌
 * @版本 1.0
 * @日期 2024-01-21 14:17
 */
public interface TraceIdGenerator {


    /**
     * 生成 traceId
     * @return traceId
     */
    String traceId();


    /**
     * 生成 spanId
     * @return spanId
     */
    String spanId();

}
