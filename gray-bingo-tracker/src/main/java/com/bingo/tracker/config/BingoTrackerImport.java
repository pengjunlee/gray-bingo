package com.bingo.tracker.config;

import com.bingo.common.constants.BingoHelperCst;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({TrackerConfiguration.class})
@ConditionalOnProperty(value = BingoHelperCst.BINGO_HELPER_CONFIG_TRACKER, havingValue = "open")
public class BingoTrackerImport {
}
