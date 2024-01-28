package com.bingo.mybatis.datasource;

import com.bingo.common.constants.BingoStringCst;
import com.bingo.common.utils.StringUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Order(-1)
@ConditionalOnProperty(name = BingoStringCst.HELPER_CONF_DYNAMIC, havingValue = BingoStringCst.BINGO_HELPER_OPEN, matchIfMissing = false)
public class DynamicDataSourceAspect {

    /**
     * 数据源切点
     */
    @Pointcut("@annotation(com.bingo.mybatis.datasource.BingoDatasource) || @within(com.bingo.mybatis.datasource.BingoDatasource)")
    public void dynamicDatasource() {
    }

    /**
     * 代码启动前指定数据源
     *
     * @param joinPoint
     */
    @Before("dynamicDatasource()")
    public void dataSourceBefore(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        /**
         * 获取方法上的数据源注解，方法上存在则取方法上的数据库，方法上不存在则取类上配置的数据源
         */
        BingoDatasource bingoDatasource = method.getAnnotation(BingoDatasource.class);
        if (bingoDatasource == null) {
            bingoDatasource = joinPoint.getTarget().getClass().getAnnotation(BingoDatasource.class);
        }

        /**
         * 如果直接配置了数据源名称，怎优先使用数据源名称获取数据源
         */
        String dbName = bingoDatasource.dbName();
        if (StringUtil.isNotBlank(dbName)) {
            DBContextHolder.setDBName(dbName);
            return;
        }
        /**
         * 其次依据固定的索引获取数据源
         */
        String dbIndex = bingoDatasource.dbIndex();
        if (StringUtil.isNotBlank(dbIndex)) {
            DBContextHolder.setDBIndex(dbIndex);
            return;
        }

        /**
         * 最后通过入参动态索引对应的数据源
         */
        int paramIndex = bingoDatasource.paramIndex() - 1;
        dbIndex = (String) joinPoint.getArgs()[paramIndex];
        DBContextHolder.setDBIndex(dbIndex);
    }

}