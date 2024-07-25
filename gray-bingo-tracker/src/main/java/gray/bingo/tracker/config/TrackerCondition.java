package gray.bingo.tracker.config;

import gray.bingo.common.constants.BingoCst;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class TrackerCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return context.getEnvironment().getProperty(BingoCst.CONF_HELPER_ENABLES).contains(BingoCst.BINGO_HELPER_TRACKER);
    }
}