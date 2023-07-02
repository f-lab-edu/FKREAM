package com.flab.fkream.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

public class HttpRequestUtils {

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static String getIpAddress() {
        return (null != getRequest().getHeader("X-FORWARDED-FOR")) ? getRequest().getHeader("X-FORWARDED-FOR") : getRequest().getRemoteAddr();
    }
}
