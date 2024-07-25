package gray.bingo.starter.logback;

import ch.qos.logback.core.PropertyDefinerBase;
import gray.bingo.common.utils.SystemUtil;

/**
 * 使用在 logback-spring.xml 中, 作为日志收集时的机器名
 *
 * @作者 Graython
 * @版本 1.0
 * @日期 2024-01-21 14:17
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
