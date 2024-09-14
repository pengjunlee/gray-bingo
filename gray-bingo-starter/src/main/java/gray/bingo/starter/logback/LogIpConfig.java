package gray.bingo.starter.logback;

import ch.qos.logback.core.PropertyDefinerBase;
import gray.bingo.common.utils.SystemUtil;

/**
 * 使用在 logback-spring.xml 中, 作为日志收集时的机器IP
 *
 * @作者 graython
 * @版本 1.0
 * @日期 2024-01-21 14:17
 */
public class LogIpConfig extends PropertyDefinerBase {

    private static final String IP;

    static {
        IP = SystemUtil.getIp();
    }

    @Override
    public String getPropertyValue() {
        return IP;
    }
}
