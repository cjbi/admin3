package tech.wetech.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.wetech.admin.model.Result;
import tech.wetech.admin.model.dto.LoginDTO;
import tech.wetech.admin.model.dto.UserTokenDTO;
import tech.wetech.admin.service.UserService;

/**
 * @author cjbi
 */
@Slf4j
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("auth/login")
    public Result<UserTokenDTO> login(@Validated @RequestBody LoginDTO loginDTO) {
        UserTokenDTO  userInfoDTO = userService.login(loginDTO);
        return Result.success(userInfoDTO);
    }

    @PostMapping("auth/logout")
    public Result logout() {
        //清除缓存
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            SecurityUtils.getSecurityManager().logout(subject);
        }
        return Result.success();
    }

}
