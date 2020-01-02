/*
 * 文  件  名：WebUtil.java
 * 版         权：Copyright 2012 JESHING Tech.Co.Ltd.All Rights Reserved.
 * 描         述：
 * 修  改  人：王波
 * 修改时间：2014-4-3
 * 修改内容：新增
 */
package tech.wetech.admin.core.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Web 工具类
 *
 * @author 王波
 * @author 赵卉华 修改为非静态方法, 增加由调用方传入HttpServletRequest的构造方法<br>
 *         解决在404和500页面, 通过RequestContextHolder.getRequestAttributes()获取Request报空指针的问题
 * @version C01 2014-4-3
 * @since v1.0
 */
public class WebUtil {

    private final HttpServletRequest request;

    /**
     * 实例
     */
    private static final WebUtil instance = new WebUtil();

    /**
     * 构造函数
     */
    private WebUtil() {
        this.request = null;
    }

    /**
     * 构造函数
     */
    public WebUtil(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * 获取实例
     *
     * @author 赵卉华
     * @return WebUtil实例
     */
    public static WebUtil getInstance() {
        return instance;
    }

    /**
     * 返回当前请求对象
     *
     * @return 当前请求对象
     */
    public HttpServletRequest getRequest() {
        if (request != null) {
            return request;
        }
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取 session
     *
     * @return session；session不存在时创建一个；
     */
    public HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取 session
     *
     * @param create SESSION不存在时是否创建
     * @return session
     */
    public HttpSession getSession(boolean create) {
        return getRequest().getSession(create);
    }

    /**
     * 获取 session id
     *
     * @return session
     */
    public String getSessionId() {
        HttpSession session = getSession(false);
        return session == null ? null : session.getId();
    }

    /**
     * 设置SESSION属性
     *
     * @author 赵卉华
     * @param name 属性名称
     * @param value 属性值
     */
    public void setSessionAttribute(String name, Object value) {
        HttpSession session = getSession();
        session.setAttribute(name, value);
    }

    /**
     * 获取session 属性
     *
     * @param name 属性名称
     * @return 属性值；session 不存在时返回null；session中没有该熟属性时返回null
     */
    public Object getSessionAttribute(String name) {
        HttpSession session = getRequest().getSession(false);
        return session != null ? session.getAttribute(name) : null;
    }

    /**
     * 获取session 属性
     *
     * @param <T> 返回对象的类型
     * @param name 属性名称
     * @param type 对象类型
     * @return 属性值；session 不存在时返回null；session中没有该熟属性时返回null
     */
    @SuppressWarnings("unchecked")
    public <T> T getSessionAttribute(String name, Class<T> type) {
        return (T) getSessionAttribute(name);
    }

    /**
     * 获取session属性列表
     *
     * @param <T> 返回对象的类型
     * @param name 属性名称
     * @param type 对象类型
     * @return 属性值列表；session 不存在时返回null；session中没有该熟属性时返回null
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getSessionAttributes(String name, Class<T> type) {
        return (List<T>) getSessionAttribute(name);
    }

    /**
     * 获取Web工程容器路径<br>
     * 如果部署为ROOT将返回空字符串, 否则返回斜杠加工程名; 如工程名为cheetah-site-web, 返回/cheetah-site-web
     *
     * @return context path
     */
    public String getContextPath() {
        return getRequest().getContextPath();
    }

    /**
     * 获取应用根目录
     *
     * @return root path
     */
    public String getWebRoot() {
        return getRequest().getSession().getServletContext().getRealPath("/");
    }

    /**
     * 获取相对于根目录的路径
     *
     * @param path 相对路径
     * @return root path
     */
    public String getWebRoot(String path) {
        return getRequest().getSession().getServletContext().getRealPath("/") + path;
    }

    /**
     * 获取IP地址
     *
     * @author 龚健
     * @return
     */
    public String getIpAddress() {
        HttpServletRequest request = getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("http_client_ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip != null && ip.indexOf(",") != -1) {
            ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
}
