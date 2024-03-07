package gray.bingo.tracker.adapter.aspect;


import gray.bingo.tracker.common.TrackerConstants;

import java.lang.annotation.*;

/**
 * 自定义注解，用于创建Trace
 *
 * @作者 二月菌
 * @版本 1.0
 * @日期 2024-01-21 14:17
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TrackerStart {

    /**
     * 是否创建新的Trace
     * <p>
     * 如果当前存在跟踪链路, 则以当前为准
     * 忽略当前已存在的路径, 会替换后续全部链路
     */

    String spanName() default "";

    String spanType() default TrackerConstants.SPAN_TYPE_ANNOTATION;

}
