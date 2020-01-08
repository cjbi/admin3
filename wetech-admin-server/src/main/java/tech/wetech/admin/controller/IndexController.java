package tech.wetech.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.wetech.admin.model.Result;
import tech.wetech.admin.model.enumeration.CommonResultStatus;

import javax.servlet.http.HttpServletRequest;


/**
 * @author cjbi
 */
@Slf4j
@RestController
public class IndexController {

//    @RequestMapping("/auth/login")
    public Result showLoginForm(HttpServletRequest request) {
        String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
        Subject subject = SecurityUtils.getSubject();
        log.info("Begin to login");
        String error = null;
        if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户不存在";
        } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "密码错误";
        } else if (ExcessiveAttemptsException.class.getName().equals(exceptionClassName)) {
            error = "登陆失败次数过多";
        } else if (exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        }
        boolean loginSuccess = error == null && subject.isAuthenticated();
        String loginErrorMsg = error == null ? "未登录" : error;
        return loginSuccess ? Result.success() : Result.failure(CommonResultStatus.LOGIN_ERROR, loginErrorMsg);
    }
}
