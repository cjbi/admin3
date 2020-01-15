package tech.wetech.admin.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.wetech.admin.aspect.SystemLog;
import tech.wetech.admin.model.Result;
import tech.wetech.admin.model.entity.Permission;
import tech.wetech.admin.service.PermissionService;

/**
 * @author cjbi
 */
@Controller
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private PermissionService permissionService;

    @RequiresPermissions("resource:view")
    @GetMapping
    public String resourcePage(Model model) {
        model.addAttribute("resourceList", permissionService.queryPermissionsByOrder());
        return "system/resource";
    }

    @ResponseBody
    @RequiresPermissions("permission:create")
    @SystemLog("资源管理创建资源")
    @PostMapping("/create")
    public Result create(@Validated Permission permission) {
        permissionService.createPermission(permission);
        return Result.success();
    }

    @ResponseBody
    @RequiresPermissions("permission:update")
    @SystemLog("资源管理更新资源")
    @PostMapping("/update")
    public Result update(@Validated Permission permission) {
        permissionService.updateNotNull(permission);
        return Result.success();
    }

    @ResponseBody
    @RequiresPermissions("resource:delete")
    @SystemLog("资源管理删除资源")
    @PostMapping("/delete")
    public Result delete(@RequestParam("id") Long id) {
        permissionService.deleteById(id);
        return Result.success();
    }

}
