package gray.bingo.common.anno;

import java.lang.annotation.*;

/**
 * 使用该注解标记的方法无需登录也可以访问
 */

@Documented
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Anonymous {
}
