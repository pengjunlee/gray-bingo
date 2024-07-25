package gray.bingo.common.config.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.CollectionUtils;

import java.util.Map;

public class ContainPropertyCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Map<String, Object> attributes = metadata.getAnnotationAttributes(KeyValueAnnotation.class.getName());
        if (CollectionUtils.isEmpty(attributes)) return false;
        String key = (String) attributes.get("key");
        String value = (String) attributes.get("value");
        String property = context.getEnvironment().getProperty(key, String.class, "");
        return property.contains(value);
    }
}
