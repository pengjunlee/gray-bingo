package gray.bingo.tracker.adapter.spring;

import gray.bingo.common.utils.StringUtil;
import gray.bingo.tracker.common.Tracker;
import gray.bingo.tracker.common.TrackerConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Rest端点的过滤器配置
 *
 * @作者 二月菌
 * @版本 1.0
 * @日期 2024-01-21 14:17
 */
@Slf4j
@Order(Integer.MIN_VALUE)
public class TrackerFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            try {
                HttpServletRequest request = (HttpServletRequest) servletRequest;
                String header = request.getHeader(TrackerConstants.HTTP_HEADERS);

                boolean isFork = false;

                String[] params = null;
                if (StringUtil.isNotBlank(header)) {
                    params = header.split("\\|");
                    if (params.length == 3) {
                        isFork = true;
                    }
                }
                if (isFork) {
                    Tracker.fork(request.getMethod() + ":" + request.getServletPath(), params[2], params[0], params[1]);
                } else {
                    Tracker.start(request.getMethod() + ":" + request.getServletPath(), TrackerConstants.SPAN_TYPE_HTTP_MVC);
                }

            } catch (Exception ignored) {
            }
            filterChain.doFilter(servletRequest, response);
        } finally {
            Tracker.end();
        }
    }

}
