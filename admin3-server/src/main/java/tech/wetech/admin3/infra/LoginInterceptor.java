package tech.wetech.admin3.infra;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import tech.wetech.admin3.common.Constants;
import tech.wetech.admin3.common.SessionItemHolder;
import tech.wetech.admin3.exception.BusinessException;
import tech.wetech.admin3.exception.CommonResultStatus;
import tech.wetech.admin3.service.SessionService;

/**
 * @author cjbi
 */
public class LoginInterceptor implements HandlerInterceptor {

    private final SessionService sessionService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public LoginInterceptor(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getHeader("Authorization") == null) {
            log.warn("Request uri {} {} is unauthorized", request.getMethod(), request.getRequestURI());
            throw new BusinessException(CommonResultStatus.UNAUTHORIZED, "未授权");
        }
        String token = request.getHeader("Authorization").replace("Bearer", "").trim();
        if (!sessionService.isLogin(token)) {
            throw new BusinessException(CommonResultStatus.UNAUTHORIZED, "未登录");
        }
        SessionItemHolder.setItem(Constants.SESSION_CURRENT_USER, sessionService.getLoginUserInfo(token));
        return true;
    }
}
