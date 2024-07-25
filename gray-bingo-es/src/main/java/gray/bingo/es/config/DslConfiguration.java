package gray.bingo.es.config;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * 描述：ES  mybatis配置
 *
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID REASON   PERSON     DATE
 *  1 Create   高福兵(01409246)   2021/10/18
 * ****************************************************************************
 * </pre>
 *
 * @author 高福兵(01409246)
 * @version 1.0
 */
public class DslConfiguration extends Configuration {

    private final static Logger logger = LoggerFactory.getLogger(DslConfiguration.class);

    public DslConfiguration(String mapperLocations) {
        super();
        try {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resource = resolver.getResources(mapperLocations.trim());
            for (Resource rs : resource) {
                logger.info("ES Parsed mapper:{}", rs.toString());
                new XMLMapperBuilder(rs.getInputStream(), this, rs.toString(), this.getSqlFragments()).parse();
            }
        } catch (Exception e) {
            logger.error("parse dsl  xml error:{}", e.getMessage(), e);
        }
    }

}