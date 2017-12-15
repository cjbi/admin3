package tech.wetech.admin.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class InitServlet extends HttpServlet {

    /**
     * 唯一序列号
     */
    private static final long serialVersionUID = -8452019304732100649L;

    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(InitServlet.class);

    private static WebApplicationContext wc;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // 初始化spring的工厂
        wc = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        // 初始化容器路劲
        logger.info("系统初始化完成");
    }

    public static WebApplicationContext getWebApplicationContext() {
        return wc;
    }

}
