package com.bingo.starter.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({BingoHelperRegister.class})
public class BingoHelperBuilderImport {
}
