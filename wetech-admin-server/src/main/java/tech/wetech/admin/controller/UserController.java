package tech.wetech.admin.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.wetech.admin.aspect.SystemLog;
import tech.wetech.admin.model.PageWrapper;
import tech.wetech.admin.model.Result;
import tech.wetech.admin.model.dto.UserPageDTO;
import tech.wetech.admin.model.entity.User;
import tech.wetech.admin.model.enumeration.CommonResultStatus;
import tech.wetech.admin.model.query.UserQuery;
import tech.wetech.admin.service.UserService;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

/**
 * @author cjbi
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @RequiresPermissions("user:view")
    public Result<PageWrapper<UserPageDTO>> queryUserList(UserQuery userQuery) {
        return Result.success(userService.queryUserPage(userQuery));
    }

    @PostMapping("create")
    @RequiresPermissions("user:create")
    @SystemLog("用户管理创建用户")
    public Result create(User user) {
        userService.createUser(user);
        return Result.success();
    }

    @PostMapping("update")
    @RequiresPermissions("user:update")
    @SystemLog("用户管理更新用户")
    public Result update(User user) {
        userService.updateNotNull(user);
        return Result.success();
    }

    @DeleteMapping
    @RequiresPermissions("user:delete")
    @SystemLog("用户管理删除用户")
    public Result deleteBatchByIds(@NotNull @RequestParam("id") Long[] ids) {
        if (isSelf(ids)) {
            return Result.failure(CommonResultStatus.FAILED_DEL_OWN);
        }
        Arrays.stream(ids).forEach(userService::deleteById);
        return Result.success();
    }

    private boolean isSelf(@RequestParam("id") @NotNull @NotNull Long[] ids) {
        // 当前用户
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        User user = userService.queryByUsername(username);
        return Arrays.stream(ids).anyMatch(id -> id.equals(user.getId()));
    }

    @RequiresPermissions("user:update")
    @PutMapping("lock")
    public Result lockUser(@RequestParam("id") Long[] ids, @RequestParam("locked") Integer locked) {
        if (isSelf(ids)) {
            return Result.failure(CommonResultStatus.FAILED_LOCK_OWN);
        }
        for (Long id : ids) {
            User user = new User();
            user.setId(id);
            user.setLocked(locked);
            userService.updateNotNull(user);
        }
        return Result.success();
    }

    @RequiresPermissions("user:update")
    @PostMapping("{id}/change/password")
    @SystemLog("用户管理更改用户密码")
    public Result changePassword(@PathVariable("id") Long id, String newPassword) {
        userService.changePassword(id, newPassword);
        return Result.success();
    }

}
