package tech.wetech.admin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.wetech.admin.model.Result;
import tech.wetech.admin.model.dto.LoginDTO;
import tech.wetech.admin.model.dto.UserInfoDTO;
import tech.wetech.admin.service.PermissionService;
import tech.wetech.admin.service.UserService;

/**
 * @author cjbi
 */
@Slf4j
@RestController
public class LoginController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserService userService;

    @PostMapping("auth/login")
    public Result<UserInfoDTO> login(@RequestBody LoginDTO loginDTO) {
        UserInfoDTO userInfoDTO = userService.login(loginDTO);
        return Result.success(userInfoDTO);
    }

    @PostMapping("auth/logout")
    public Result logout() {
        return Result.success();
    }

}
