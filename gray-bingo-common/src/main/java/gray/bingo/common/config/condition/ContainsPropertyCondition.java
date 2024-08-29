package gray.bingo.common.config.condition;

import gray.bingo.common.anno.Property;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.CollectionUtils;

import java.util.Map;

public class ContainsPropertyCondition implements Condition {
    @Override
    public boolean matches(@NotNull ConditionContext context, AnnotatedTypeMetadata metadata) {
        Map<String, Object> attributes = metadata.getAnnotationAttributes(Property.class.getName());
        if (CollectionUtils.isEmpty(attributes)) return false;
        String key = (String) attributes.get("key");
        String value = (String) attributes.get("value");
        String property = context.getEnvironment().getProperty(key, String.class, "");
        return property.contains(value);
    }
}
