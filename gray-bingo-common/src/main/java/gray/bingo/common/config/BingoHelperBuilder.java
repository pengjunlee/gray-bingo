package gray.bingo.common.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.type.AnnotationMetadata;

/**
 * BingoHelper辅助工具类抽象父类
 */
public abstract class BingoHelperBuilder {

    public abstract void build(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry);

    public abstract String helperName();
}
