package gray.bingo.tracker.adapter.annotation;

import gray.bingo.common.utils.StringUtil;
import gray.bingo.tracker.common.SpanContext;
import gray.bingo.tracker.common.Tracker;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * TrackerStart创建追踪切面
 *
 * @作者 二月の菌
 * @版本 1.0
 * @日期 2024-01-21 14:17
 */
@Slf4j
@Aspect
public class TrackerStartAspect {

    /**
     * 通过注解 start 或 fork 到追踪中
     *
     * @param point        方法
     * @param trackerStart 注解
     * @return 返回值
     */
    @Around("@annotation(trackerStart)")
    public Object around(ProceedingJoinPoint point, TrackerStart trackerStart) throws Throwable {
        SpanContext span = null;
        try {
            String spanName = trackerStart.spanName();
            if (StringUtil.isBlank(spanName)) {
                // 取方法名称
                spanName = point.getSignature().getDeclaringTypeName();
            }
            span = Tracker.start(spanName, trackerStart.spanType());
            return point.proceed(point.getArgs());
        } finally {
            if (span != null) {
                Tracker.end();
            }
        }
    }
}
