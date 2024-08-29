package gray.bingo.common.anno;

import gray.bingo.common.config.condition.ContainsPropertyCondition;
import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(ContainsPropertyCondition.class)
public @interface Property {
    String key();

    String value();
}