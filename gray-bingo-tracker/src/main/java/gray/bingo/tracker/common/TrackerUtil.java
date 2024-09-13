package gray.bingo.tracker.common;


import gray.bingo.common.utils.StringUtil;

/**
 * Trace 对 HTTP 请求处理的相关工具类
 *
 * @author graython
 */
public class TrackerUtil {

    /**
     * 构造一个 Trace 请求头
     */
    public static String buildHeader(String type) {
        String traceId = Tracker.getTraceId();
        String spanParentId = Tracker.getSpanId();
        if (StringUtil.isNotBlank(traceId) && StringUtil.isNotBlank(spanParentId)) {
            return String.format("%s|%s|%s", traceId, spanParentId, type);
        }
        return "";
    }

    public static boolean checkIgnoreApi(String spanName) {
        for (String api : TrackerCst.ignoreInnerApi) {
            if (spanName.contains(api)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkIgnoreSql(String spanName) {
        for (String api : TrackerCst.ignoreInnerSql) {
            if (spanName.contains(api)) {
                return true;
            }
        }
        return false;
    }
}
