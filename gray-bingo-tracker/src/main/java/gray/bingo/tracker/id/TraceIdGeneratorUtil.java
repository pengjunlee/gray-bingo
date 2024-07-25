package gray.bingo.tracker.id;

/**
 * 追踪ID生成
 *
 * @作者 Graython
 * @版本 1.0
 * @日期 2024-01-21 14:17
 */
public class TraceIdGeneratorUtil {
    /**
     * TraceGenerator traceId 生成器
     */
    private static final TraceIdGenerator traceGenerator = new TraceIdGeneratorUUID();

    public static String traceId() {
        return traceGenerator.traceId();
    }

    public static String spanId() {
        return traceGenerator.spanId();
    }
}
