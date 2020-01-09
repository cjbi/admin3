package tech.wetech.admin.shiro;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import tech.wetech.admin.model.Result;
import tech.wetech.admin.model.enumeration.CommonResultStatus;
import tech.wetech.admin.shiro.spring.SpringCacheManagerWrapper;
import tech.wetech.admin.utils.JsonUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author cjbi
 */
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {

    private SpringCacheManagerWrapper springCacheManagerWrapper;

    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader(SecurityConsts.REQUEST_AUTH_HEADER);
        return authorization != null;
    }

    /**
     * 登录验证
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader(SecurityConsts.REQUEST_AUTH_HEADER);

        JwtToken token = new JwtToken(authorization);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);

        // 绑定上下文
        String account = JwtUtil.getClaim(authorization, SecurityConsts.ACCOUNT);
        UserContext userContext = new UserContext(new LoginUser(account));

        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 刷新AccessToken，进行判断RefreshToken是否过期，未过期就返回新的AccessToken且继续正常访问
     */
    private boolean refreshToken(ServletRequest request, ServletResponse response) {
        // 获取AccessToken(Shiro中getAuthzHeader方法已经实现)
        String token = this.getAuthzHeader(request);
        // 获取当前Token的帐号信息
        String account = JwtUtil.getClaim(token, SecurityConsts.ACCOUNT);
        String refreshTokenCacheKey = SecurityConsts.PREFIX_SHIRO_REFRESH_TOKEN + account;
        // 判断Redis中RefreshToken是否存在
//        if (cacheClient.exists(refreshTokenCacheKey)) {
//            // 获取RefreshToken时间戳,及AccessToken中的时间戳
//            // 相比如果一致，进行AccessToken刷新
//            String currentTimeMillisRedis = cacheClient.get(refreshTokenCacheKey);
//            String tokenMillis=JwtUtil.getClaim(token, SecurityConsts.CURRENT_TIME_MILLIS);
//
//            if (tokenMillis.equals(currentTimeMillisRedis)) {
//
//                // 设置RefreshToken中的时间戳为当前最新时间戳
//                String currentTimeMillis = String.valueOf(System.currentTimeMillis());
//                Integer refreshTokenExpireTime = jwtProperties.refreshTokenExpireTime;
//                cacheClient.set(refreshTokenCacheKey, currentTimeMillis,refreshTokenExpireTime*60l);
//
//                // 刷新AccessToken，为当前最新时间戳
//                token = JwtUtil.sign(account, currentTimeMillis);
//
//                // 使用AccessToken 再次提交给ShiroRealm进行认证，如果没有抛出异常则登入成功，返回true
//                JwtToken jwtToken = new JwtToken(token);
//                this.getSubject(request, response).login(jwtToken);
//
//                // 设置响应的Header头新Token
//                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//                httpServletResponse.setHeader(SecurityConsts.REQUEST_AUTH_HEADER, token);
//                httpServletResponse.setHeader("Access-Control-Expose-Headers", SecurityConsts.REQUEST_AUTH_HEADER);
//                return true;
//            }
//        }
        return false;
    }

    /**
     * 是否允许访问
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                this.executeLogin(request, response);
            } catch (Exception e) {
                String msg = e.getMessage();
                Throwable throwable = e.getCause();
                if (throwable != null && throwable instanceof SignatureVerificationException) {
                    msg = "Token或者密钥不正确(" + throwable.getMessage() + ")";
                } else if (throwable != null && throwable instanceof TokenExpiredException) {
                    // AccessToken已过期
                    if (this.refreshToken(request, response)) {
                        return true;
                    } else {
                        msg = "Token已过期(" + throwable.getMessage() + ")";
                    }
                } else {
                    if (throwable != null) {
                        msg = throwable.getMessage();
                    }
                }
                this.response401(request, response, msg);
                return false;
            }
        }
        return true;
    }

    /**
     * 401非法请求
     * @param req
     * @param resp
     */
    private void response401(ServletRequest req, ServletResponse resp,String msg) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = httpServletResponse.getWriter();

            out.append(JsonUtil.toJson(Result.failure(CommonResultStatus.LOGIN_ERROR,"密码不正确")));
        } catch (IOException e) {
            log.error("返回Response信息出现IOException异常:" + e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }


}
