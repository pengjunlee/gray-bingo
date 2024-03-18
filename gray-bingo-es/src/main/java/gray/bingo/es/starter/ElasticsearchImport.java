package gray.bingo.es.starter;

import gray.bingo.common.config.BingoProp;
import gray.bingo.es.config.DslConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 动态数据源加载注入类
 */
@Configuration
public class ElasticsearchImport {

    @Bean
    public DslConfiguration dSLConfiguration() {
        return new DslConfiguration(BingoProp.getProperty("sapper.elasticsearch.mapper.locations", String.class));
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}