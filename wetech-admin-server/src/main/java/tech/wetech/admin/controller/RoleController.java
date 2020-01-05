package tech.wetech.admin.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.wetech.admin.aspect.SystemLog;
import tech.wetech.admin.model.Result;
import tech.wetech.admin.model.entity.Role;
import tech.wetech.admin.service.ResourceService;
import tech.wetech.admin.service.RoleService;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

/**
 * @author cjbi
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    @GetMapping
    @RequiresPermissions("role:view")
    public String rolePage(Model model) {
        model.addAttribute("resourceList", resourceService.queryAll());
        return "system/role";
    }

    @ResponseBody
    @RequiresPermissions("role:create")
    @SystemLog("角色管理创建角色")
    @PostMapping("/create")
    public Result create(@Validated Role role) {
        roleService.create(role);
        return Result.success();
    }

    @ResponseBody
    @RequiresPermissions("role:update")
    @SystemLog("角色管理更新角色")
    @PostMapping("/update")
    public Result update(@Validated Role role) {
        roleService.updateNotNull(role);
        return Result.success();
    }

    @ResponseBody
    @RequiresPermissions("role:delete")
    @SystemLog("角色管理删除角色")
    @PostMapping("/delete-batch")
    public Result deleteBatchByIds(@NotNull @RequestParam("id") Long[] ids) {
        Arrays.stream(ids).forEach(roleService::deleteById);
        return Result.success();
    }

}
