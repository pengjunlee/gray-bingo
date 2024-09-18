package gray.bingo.mybatis.config;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Intercepts({@Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
), @Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}
), @Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class,}
)})
@ConditionalOnProperty(name = "bingo.log-sql", havingValue = "true", matchIfMissing = false)
public class SqlLogInterceptor implements Interceptor {
    private static final Logger log = LoggerFactory.getLogger(SqlLogInterceptor.class);


    public static final String UNKNOWN = "UNKNOWN";
    public static final String SQL_PLACEHOLDER = "#{%s}";


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (log.isDebugEnabled()) {
            String completeSql = "";
            try {
                log.debug("-------开始执行打印拦截器------");
                completeSql = getCompleteSqlInfo(invocation);
            } catch (RuntimeException e) {
                log.error("获取sql信息出错,异常信息", e);
            } finally {
                log.debug("sql执行信息:[{}]", completeSql);
                log.debug("-------退出打印拦截器------");
            }
        }
        return invocation.proceed();
    }


    /**
     * 获取打印sql
     *
     * @param invocation
     * @return
     */
    private String getCompleteSqlInfo(Invocation invocation) {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        return generateCompleteSql(mappedStatement, parameter);
    }


    private String generateCompleteSql(MappedStatement mappedStatement, Object parameter) {
        String mappedStatementId = mappedStatement.getId();
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        String sql = boundSql.getSql().trim();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        Object parameterObject = boundSql.getParameterObject();
        Configuration configuration = mappedStatement.getConfiguration();
        if (!CollectionUtils.isEmpty(parameterMappings) && parameterObject != null) {
            for (int i = 0; i < parameterMappings.size(); i++) {
                String replacePlaceHolder = String.format(SQL_PLACEHOLDER, i);
                sql = sql.replaceFirst("\\?", replacePlaceHolder);
            }
            MetaObject metaObject = configuration.newMetaObject(parameterObject);
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                String replacePlaceHolder = String.format(SQL_PLACEHOLDER, i);
                String propertyName = parameterMapping.getProperty();
                if (metaObject.hasGetter(propertyName)) {
                    Object obj = metaObject.getValue(propertyName);
                    sql = sql.replaceFirst(Pattern.quote(replacePlaceHolder), Matcher.quoteReplacement(getParameterValue(obj)));
                } else if (boundSql.hasAdditionalParameter(propertyName)) {
                    Object obj = boundSql.getAdditionalParameter(propertyName);
                    sql = sql.replaceFirst(Pattern.quote(replacePlaceHolder), Matcher.quoteReplacement(getParameterValue(obj)));
                } else {
                    sql = sql.replaceFirst(Pattern.quote(replacePlaceHolder), UNKNOWN);
                }
            }
        }
        StringBuilder formatSql = new StringBuilder().append("mappedStatementId-ID:").append(mappedStatementId).append("\n").append("Execute SQL:").append(sql);
        return formatSql.toString();
    }


    private String getParameterValue(Object obj) {
        String value = "";
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value;
    }


    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
}