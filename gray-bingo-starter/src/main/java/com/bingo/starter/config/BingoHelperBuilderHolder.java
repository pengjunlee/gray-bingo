package com.bingo.starter.config;

import com.bingo.common.Enums.BingoHelperEnum;
import com.bingo.common.config.BingoHelperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Helper建造者的持有者对象
 *
 * @作者 二月君
 * @版本 1.0
 * @日期 2024-01-21 16:52
 */
@Slf4j
public class BingoHelperBuilderHolder implements ImportBeanDefinitionRegistrar {

    private static final List<String> BUILDER_LIST = new ArrayList<>();

    private static AnnotationMetadata annotationMetadata;

    private static BeanDefinitionRegistry beanDefinitionRegistry;

    static {
        List<String> builderClazzList = Arrays.stream(BingoHelperEnum.values()).map(BingoHelperEnum::getBuilderClazz).collect(Collectors.toList());
        BUILDER_LIST.addAll(builderClazzList);
    }

    public static void registryAllBuilders(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        log.info("[      HELPER_BUILDER] >>> 开始执行 [ {} ]", BingoHelperBuilderHolder.class);
        BingoHelperBuilderHolder.annotationMetadata = annotationMetadata;
        BingoHelperBuilderHolder.beanDefinitionRegistry = beanDefinitionRegistry;
        BUILDER_LIST.forEach(BingoHelperBuilderHolder::registry);
        log.info("[      HELPER_BUILDER] >>> 执行结束 [ {} ]", BingoHelperBuilderHolder.class);
    }

    public static void registry(String clazzName) {
        BingoHelperBuilder helperBuilder = null;
        try {
            helperBuilder = (BingoHelperBuilder) Class.forName(clazzName).newInstance();
        } catch (ClassNotFoundException cnfe) {
            log.info("[      HELPER_BUILDER]  -- 注册组件 [ {} ] 失败，原因:类未找到！", clazzName);
            return;
        } catch (Exception e) {
            log.info("[      HELPER_BUILDER]  -- 注册组件 [ {} ] 失败，原因:{}！", clazzName, e.getMessage());
            return;
        }
        helperBuilder.build(BingoHelperBuilderHolder.annotationMetadata, BingoHelperBuilderHolder.beanDefinitionRegistry);
    }
}
