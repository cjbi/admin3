package tech.wetech.admin.controller.system;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tech.wetech.admin.common.base.PageResultSet;
import tech.wetech.admin.common.base.Result;
import tech.wetech.admin.common.base.ResultCodeEnum;
import tech.wetech.admin.model.system.entity.User;
import tech.wetech.admin.service.system.OrganizationService;
import tech.wetech.admin.service.system.RoleService;
import tech.wetech.admin.service.system.UserService;
import tech.wetech.admin.common.annotation.SystemLog;
import tech.wetech.admin.controller.BaseController;
import tech.wetech.admin.model.system.response.UserDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;

/**
 * @author cjbi
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    @RequiresPermissions("user:view")
    public String toPage(Model model) {
        setCommonData(model);
        return "system/user";
    }

    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("user:view")
    public PageResultSet<UserDto> list(User user) {
        return userService.findByPage(user);
    }

    @ResponseBody
    @PostMapping("/create")
    @RequiresPermissions("user:create")
    @SystemLog("用户管理创建用户")
    public Result create(@Valid User user) {
        userService.createUser(user);
        return Result.success();
    }

    @ResponseBody
    @PostMapping("/update")
    @RequiresPermissions("user:update")
    @SystemLog("用户管理更新用户")
    public Result update(User user) {
        userService.updateUser(user);
        return Result.success();
    }

    @ResponseBody
    @PostMapping("/delete")
    @RequiresPermissions("user:delete")
    @SystemLog("用户管理删除用户")
    public Result delete(@RequestParam("id") Long[] ids, HttpServletRequest request) {
        // 当前用户
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        User user = userService.findByUsername(username);
        boolean isSelf = Arrays.stream(ids).anyMatch(id -> id.equals(user.getId()));
        if (isSelf) {
            return Result.failure(ResultCodeEnum.FAILED_DEL_OWN);
        }
        Arrays.asList(ids).forEach(id -> userService.deleteUser(id));
        return Result.success();
    }

    @ResponseBody
    @RequiresPermissions("user:update")
    @PostMapping("/{id}/change/password")
    @SystemLog("用户管理更改用户密码")
    public Result changePassword(@PathVariable("id") Long id, String newPassword) {
        userService.changePassword(id, newPassword);
        return Result.success();
    }

    private void setCommonData(Model model) {
        model.addAttribute("organizationList", organizationService.findAll());
        model.addAttribute("roleList", roleService.findAll());
    }

}
