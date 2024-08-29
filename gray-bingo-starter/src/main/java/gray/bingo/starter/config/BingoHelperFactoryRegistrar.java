package gray.bingo.starter.config;

import gray.bingo.common.Enums.BingoHelperEnum;
import gray.bingo.common.config.BingoHelperFactory;
import gray.bingo.common.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;

/**
 * Helper建造者的注册管理类
 *
 * @作者 二月の菌
 * @版本 1.0
 * @日期 2024-01-21 16:52
 */
@Slf4j
public class BingoHelperFactoryRegistrar implements ImportBeanDefinitionRegistrar {

    private static AnnotationMetadata annotationMetadata;

    private static BeanDefinitionRegistry beanDefinitionRegistry;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        BingoHelperFactoryRegistrar.registryAllBuilders(annotationMetadata,beanDefinitionRegistry);
    }

    public static void registryAllBuilders(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        log.info("[     HELPER_FACTORY] >>> 开始注册 [ {} ]", BingoHelperFactoryRegistrar.class);
        BingoHelperFactoryRegistrar.annotationMetadata = annotationMetadata;
        BingoHelperFactoryRegistrar.beanDefinitionRegistry = beanDefinitionRegistry;
        Arrays.stream(BingoHelperEnum.values()).forEach(BingoHelperFactoryRegistrar::registry);
        log.info("[     HELPER_FACTORY] >>> 注册完成 [ {} ]", BingoHelperFactoryRegistrar.class);
    }

    public static void registry(BingoHelperEnum helperEnum) {
        if (StringUtil.isBlank(helperEnum.getBuilderClazz())) {
            return;
        }
        BingoHelperFactory helperBuilder = null;
        try {
            helperBuilder = (BingoHelperFactory) Class.forName(helperEnum.getBuilderClazz()).newInstance();
        } catch (ClassNotFoundException cnfe) {
            log.info("[     HELPER_FACTORY]  -- 注册组件 [ {} ] 失败，原因:[ {} ] class not found！", helperEnum.getCode(), helperEnum.getBuilderClazz());
            return;
        } catch (Exception e) {
            log.info("[     HELPER_FACTORY]  -- 注册组件 [ {} ] 失败，原因: {}！", helperEnum.getCode(), e.getMessage());
            return;
        }
        helperBuilder.build(BingoHelperFactoryRegistrar.annotationMetadata, BingoHelperFactoryRegistrar.beanDefinitionRegistry);
    }
}
