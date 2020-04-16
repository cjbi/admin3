package tech.wetech.admin.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.wetech.admin.model.Result;
import tech.wetech.admin.model.dto.RoleDTO;
import tech.wetech.admin.model.entity.Role;
import tech.wetech.admin.service.RoleService;

import java.util.List;

/**
 * @author cjbi
 */
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    @RequiresPermissions("role:view")
    public Result<List<RoleDTO>> queryRoleList() {
        return Result.success(roleService.queryAllRole());
    }

    @RequiresPermissions("role:create")
    @PostMapping
    public Result create(@RequestBody Role role) {
        roleService.create(role);
        return Result.success();
    }

    @RequiresPermissions("role:update")
    @PutMapping
    public Result update(@RequestBody Role role) {
        roleService.updateNotNull(role);
        return Result.success();
    }

    @RequiresPermissions("role:delete")
    @DeleteMapping("{id}")
    public Result deleteByIds(@PathVariable("id") Long id) {
        roleService.deleteById(id);
        return Result.success();
    }

}
