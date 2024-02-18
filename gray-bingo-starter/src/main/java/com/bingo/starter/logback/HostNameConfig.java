package com.bingo.starter.logback;

import ch.qos.logback.core.PropertyDefinerBase;
import com.bingo.starter.utils.SystemUtil;

/**
 * 使用在 logback-spring.xml 中, 作为日志收集时的机器名
 *
 * @author xzzz
 */
public class HostNameConfig extends PropertyDefinerBase {

    private static final String HOST_NAME;

    static {
        HOST_NAME = SystemUtil.getHostName();
    }

    @Override
    public String getPropertyValue() {
        return HOST_NAME;
    }
}
