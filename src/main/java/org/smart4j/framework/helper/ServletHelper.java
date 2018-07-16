package org.smart4j.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet助手类
 */
public class ServletHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServletHelper.class);

    /**
     * 每个线程独自拥有一份副本
     */
    private static final ThreadLocal<ServletHelper> SERVLET_HELPER_THREAD_LOCAL = new ThreadLocal<>();

    private HttpServletRequest request;
    private HttpServletResponse response;

    private ServletHelper(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    /**
     * 初始化
     * @param request
     * @param response
     */
    public static void init(HttpServletRequest request, HttpServletResponse response) {
        SERVLET_HELPER_THREAD_LOCAL.set(new ServletHelper(request, response));
    }

    /**
     * 销毁
     */
    public static void destory() {
        SERVLET_HELPER_THREAD_LOCAL.remove();
    }

    /**
     * 获取request
     * @return
     */
    public static HttpServletRequest getRequest() {
        return SERVLET_HELPER_THREAD_LOCAL.get().request;
    }

    /**
     * 获取response
     * @return
     */
    public static HttpServletResponse getResponse() {
        return SERVLET_HELPER_THREAD_LOCAL.get().response;
    }

    /**
     * 获取session
     * @return
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取servletContext
     * @return
     */
    public static ServletContext getServletContext() {
        return getRequest().getServletContext();
    }

    /**
     * 设置request Attribute
     * @param key
     * @param value
     */
    public static void setRequestAttribute(String key, String value) {
        getRequest().setAttribute(key, value);
    }

    /**
     * 获取request attribute
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T getRequestAttribute(String key) {
        return (T)getRequest().getAttribute(key);
    }

    /**
     * 移除request属性
     * @param key
     */
    public static void removeRequestAttribute(String key) {
        getRequest().removeAttribute(key);
    }

    /**
     * 发送重定向
     * @param location
     */
    public static void sendRedirect(String location) {
        try {
            getResponse().sendRedirect(location);
        } catch (IOException e) {
            LOGGER.error("redirect failure", e);
        }
    }

    /**
     * 设置session属性
     * @param key
     * @param value
     */
    public static void setSessionArrtibute(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    /**
     * 获取session属性
     * @param key
     * @param <T>
     * @return
     */
    public static <T> T getSessionAttribute(String key) {
        return (T)getSession().getAttribute(key);
    }

    /**
     * 移除session属性
     * @param key
     */
    public static void removeSessionAttribute(String key) {
        getSession().removeAttribute(key);
    }

    /**
     * 使session失效
     */
    public static void invaildateSession() {
        getSession().invalidate();
    }
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
}
