package tech.wetech.admin.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.AccessControlFilter;
import tech.wetech.admin.model.Result;
import tech.wetech.admin.model.enumeration.CommonResultStatus;
import tech.wetech.admin.util.JSONUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author cjbi
 */
@Slf4j
public class JwtFilter extends AccessControlFilter {

    protected static final String AUTHORIZATION_HEADER = "Access-Token";

    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader(AUTHORIZATION_HEADER);
        return authorization != null;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        if (isLoginAttempt(request, response)) {
            //生成jwt token
            JwtToken token = new JwtToken(req.getHeader(AUTHORIZATION_HEADER));
            //委托给Realm进行验证
            try {
                getSubject(request, response).login(token);
                return true;
            } catch (Exception e) {
            }
        }
        onLoginFail(response);
        return false;
    }

    /**
     * 登录失败时默认返回401状态码
     *
     * @param response
     * @throws IOException
     */
    private void onLoginFail(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.setContentType("application/json;charset=utf-8");
        httpResponse.getWriter().write(JSONUtil.toJSONString(Result.failure(CommonResultStatus.LOGIN_ERROR)));
    }

}
