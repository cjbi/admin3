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

    @RequestMapping("/auth/login")
    public Result showLoginForm(HttpServletRequest request) {
        String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
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
        if (error != null) {
            return Result.failure(CommonResultStatus.LOGIN_ERROR, error);
        }
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            return Result.failure(CommonResultStatus.LOGIN_ERROR, "未登录");
        }
        return Result.success();
    }
}
