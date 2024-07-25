package gray.bingo.common.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Objects;

public class RequestUtil {

    public static String getSessionID(){
        return getCookieValue("SESSION");
    }

    public static String getReferer(){
        return getHeaderValue("referer");
    }

    public static String getCookieValue(String cookieKey){
        if (Objects.isNull(cookieKey)){
            return null;
        }
        Cookie[] cookies = getRequest().getCookies();
        if (cookies == null || cookies.length == 0){
            return null;

        }
        for (Cookie cookie:cookies){
            if (cookieKey.equalsIgnoreCase(cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }

    public static String getHeaderValue(String headKey){
        HttpServletRequest req = getRequest();
        if (Objects.isNull(req)){
            return null;

        }
        Enumeration<String> headerNames = req.getHeaderNames();
        if (Objects.isNull(headerNames)){
            return null;
        }
        while (headerNames.hasMoreElements()){
            String key = (String) headerNames.nextElement();
            if (headKey.equalsIgnoreCase(key)) return req.getHeader(key);
        }
        return null;
    }

    public static HttpServletRequest getRequest(){
        ServletRequestAttributes servletRequestAttributes = getRequestAttributes();
        return Objects.isNull(servletRequestAttributes) ? null:servletRequestAttributes.getRequest();
    }

    public static HttpServletResponse getResponse(){
        ServletRequestAttributes servletRequestAttributes = getRequestAttributes();
        return Objects.isNull(servletRequestAttributes) ? null:servletRequestAttributes.getResponse();
    }

    public static ServletRequestAttributes getRequestAttributes(){
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return Objects.isNull(attributes) ? null: (ServletRequestAttributes) attributes;
    }
}
