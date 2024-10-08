package gray.bingo.mybatis.datasource;

import gray.bingo.common.utils.StringUtil;
import gray.bingo.mybatis.config.DynamicDSCondition;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Order(-1)
@Conditional(DynamicDSCondition.class)
public class DynamicDataSourceAspect {

    /**
     * 数据源切点
     */
    @Pointcut("@annotation(gray.bingo.mybatis.datasource.BingoDatasource) || @within(gray.bingo.mybatis.datasource.BingoDatasource)")
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
         * 最后通过入参动态索引对应的数据源
         */
        int paramIndex = bingoDatasource.paramIndex() - 1;
        dbName = (String) joinPoint.getArgs()[paramIndex];
        DBContextHolder.setDBName(dbName);
    }

}