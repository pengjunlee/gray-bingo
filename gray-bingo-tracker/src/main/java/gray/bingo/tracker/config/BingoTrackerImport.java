package gray.bingo.tracker.config;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({TrackerProperties.class, TrackerConfiguration.class})
@Conditional(TrackerCondition.class)
public class BingoTrackerImport {
}
