package gray.bingo.starter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({BingoHelperFactoryRegistrar.class})
public class BingoHelperFactoryImport {
}
