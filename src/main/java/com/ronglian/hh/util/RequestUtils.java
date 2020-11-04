package com.ronglian.hh.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

/**
 * @author tolly
 * @ClassName: RequestUtils
 * @Description:
 * @date 2013-7-5 下午3:15:36
 */
public class RequestUtils {

    protected static Logger logger = LoggerFactory.getLogger(RequestUtils.class);

    /**
     * 取得HttpSession的简化函数.
     */
    public static HttpSession getSession() {

        return getRequest().getSession();
    }

    public static ServletRequestAttributes getServletRequestAttributes() {

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sa = (ServletRequestAttributes) ra;
        return sa;
    }

    /**
     * 取得HttpRequest的简化函数.
     */
    public static HttpServletRequest getRequest() {

        return getServletRequestAttributes().getRequest();
    }

    /**
     * 取得HttpRequest中Parameter的简化方法.
     */
    public static String getParameter(String name) {

        return getRequest().getParameter(name);
    }

    /**
     * getReqTime: 记录请求访问的时间 . <br/>
     *
     * @author tolly
     */
    public static void getReqTime(Map<String, String> tempMap) {

        tempMap.put("reqTime", StringUtils.getNowDT("yyyyMMddHHmmss"));
    }

    /**
     * 获取Cookie
     *
     * @param name
     *
     * @return
     */
    public static String getCookie(HttpServletRequest request, String name) {

        String host = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    try {
                        host = URLDecoder.decode(cookie.getValue(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        logger.error("解析cookie出错", e);
                    }
                    break;
                }
            }
        }
        return host;
    }

    public static String getServletPath() {
        return RequestUtils.getRequest().getContextPath();
    }
}
