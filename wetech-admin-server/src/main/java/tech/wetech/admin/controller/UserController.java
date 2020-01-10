package tech.wetech.admin.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.wetech.admin.aspect.SystemLog;
import tech.wetech.admin.model.PageWrapper;
import tech.wetech.admin.model.Result;
import tech.wetech.admin.model.entity.Group;
import tech.wetech.admin.model.entity.Organization;
import tech.wetech.admin.model.entity.Role;
import tech.wetech.admin.model.entity.User;
import tech.wetech.admin.model.enumeration.CommonResultStatus;
import tech.wetech.admin.model.query.PageQuery;
import tech.wetech.admin.model.vo.UserVO;
import tech.wetech.admin.service.OrganizationService;
import tech.wetech.admin.service.RoleService;
import tech.wetech.admin.service.UserService;
import tech.wetech.mybatis.domain.Page;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author cjbi
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private RoleService roleService;


    @GetMapping
    @RequiresPermissions("user:view")
    public String userPage(Model model) {
        model.addAttribute("organizationList", organizationService.queryAll());
        model.addAttribute("roleList", roleService.queryAll());
        return "system/user";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("user:view")
    public Result queryList(User user, PageQuery pageQuery) {
        Page<User> page = (Page<User>) userService.queryList(user, pageQuery);
        List<UserVO> userVOS = new ArrayList<>();
        page.forEach(u -> {
            UserVO userVO = new UserVO(u);
            userVO.setOrganizationName(getOrganizationName(userVO.getOrganizationId()));
            userVO.setRoleNames(getRoleNames(userVO.getRoleIdList()));
            userVOS.add(userVO);
        });
        return Result.success(new PageWrapper(userVOS, page.getTotal()));
    }


    private String getRoleNames(Collection<Long> groupIds) {
        if (CollectionUtils.isEmpty(groupIds)) {
            return "";
        }

        StringBuilder s = new StringBuilder();
        for (Long roleId : groupIds) {
            Role role = roleService.queryById(roleId);
            if (role != null) {
                s.append(role.getDescription());
                s.append(",");
            }
        }

        if (s.length() > 0) {
            s.deleteCharAt(s.length() - 1);
        }

        return s.toString();
    }

    private String getOrganizationName(Long organizationId) {
        Organization queryModel = new Organization();
        queryModel.setId(organizationId);
        Organization organization = organizationService.queryOne(queryModel);
        if (organization == null) {
            return "";
        }
        return organization.getName();
    }

    @ResponseBody
    @PostMapping("/create")
    @RequiresPermissions("user:create")
    @SystemLog("用户管理创建用户")
    public Result create(@Validated(User.UserCreateChecks.class) User user) {
        userService.createUser(user);
        return Result.success();
    }

    @ResponseBody
    @PostMapping("/update")
    @RequiresPermissions("user:update")
    @SystemLog("用户管理更新用户")
    public Result update(@Validated(User.UserUpdateChecks.class) User user) {
        userService.updateNotNull(user);
        return Result.success();
    }

    @ResponseBody
    @PostMapping("/delete-batch")
    @RequiresPermissions("user:delete")
    @SystemLog("用户管理删除用户")
    public Result deleteBatchByIds(@NotNull @RequestParam("id") Long[] ids) {
        // 当前用户
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        User user1 = new User();
        user1.setUsername(username);
        User user = userService.queryOne(user1);
        boolean isSelf = Arrays.stream(ids).anyMatch(id -> id.equals(user.getId()));
        if (isSelf) {
            return Result.failure(CommonResultStatus.FAILED_DEL_OWN);
        }
        Arrays.stream(ids).forEach(userService::deleteById);
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

}
