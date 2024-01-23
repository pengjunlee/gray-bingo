package com.pengjunlee.bingo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

@Slf4j
public class BingoHelperBuilderHolder implements ImportBeanDefinitionRegistrar {

    private static final List<String> BUILDER_LIST = new ArrayList<>();

    private static AnnotationMetadata annotationMetadata;

    private static BeanDefinitionRegistry beanDefinitionRegistry;

    static {
        BUILDER_LIST.add("com.pengjunlee.bingo.caffeine.CaffeineHelperBuilder");
    }

    public static void registryAllBuilders(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry)
    {
        log.info("GrayBuilderHolder 开始扫描组件...");
        log.info("***********************************************************");
        BingoHelperBuilderHolder.annotationMetadata = annotationMetadata;
        BingoHelperBuilderHolder.beanDefinitionRegistry = beanDefinitionRegistry;
        BUILDER_LIST.forEach(BingoHelperBuilderHolder::registry);
        log.info("***********************************************************");
        log.info("GrayBuilderHolder 注册组件结束...");
    }

    public static void registry(String clazzName){
        BingoHelperBuilder helperBuilder = null;
        try {
            helperBuilder = (BingoHelperBuilder)Class.forName(clazzName).newInstance();
        }catch (ClassNotFoundException cnfe){
            log.error("组件[ {} ]加载失败，原因:类未找到",clazzName);
            return;
        }catch (Exception e){
            log.error("组件[ {} ]加载失败，原因:{}",clazzName,e);
            return;
        }
        log.info("启用组件-[ {} ]",clazzName);
        helperBuilder.build();

    }
}
