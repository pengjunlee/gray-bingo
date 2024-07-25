package gray.bingo.tracker.adapter.annotation;


import gray.bingo.tracker.common.TrackerCst;

import java.lang.annotation.*;

/**
 * 自定义注解，用于创建Trace
 *
 * @作者 Graython
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

    String spanType() default TrackerCst.SPAN_TYPE_ANNOTATION;

}
