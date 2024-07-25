package gray.bingo.common.config.condition;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(ContainPropertyCondition.class)
public @interface KeyValueAnnotation {
    String key();

    String value();
}