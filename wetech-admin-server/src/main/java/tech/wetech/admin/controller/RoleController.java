package tech.wetech.admin.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tech.wetech.admin.aspect.SystemLog;
import tech.wetech.admin.model.Result;
import tech.wetech.admin.model.entity.Role;
import tech.wetech.admin.model.vo.RoleVO;
import tech.wetech.admin.service.PermissionService;
import tech.wetech.admin.service.RoleService;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author cjbi
 */
@RestController
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    @RequiresPermissions("role:view")
    public Result<List<RoleVO>> queryRoleList() {
        List<Role> roles = roleService.queryAll();
        List<RoleVO> list = new ArrayList<>();
        for (Role role : roles) {
            RoleVO roleVO = new RoleVO();
            roleVO.setId(role.getId());
            roleVO.setRole(role.getRole());
            roleVO.setName(role.getName());
            roleVO.setDescription(role.getDescription());
            roleVO.setStatus(role.getStatus());
            list.add(roleVO);
        }
        return Result.success(list);
    }

    @RequiresPermissions("role:create")
    @SystemLog("角色管理创建角色")
    @PostMapping
    public Result create(@Validated Role role) {
        roleService.create(role);
        return Result.success();
    }

    @RequiresPermissions("role:update")
    @SystemLog("角色管理更新角色")
    @PutMapping
    public Result update(@Validated Role role) {
        roleService.updateNotNull(role);
        return Result.success();
    }

    @RequiresPermissions("role:delete")
    @SystemLog("角色管理删除角色")
    @DeleteMapping
    public Result deleteBatchByIds(@NotNull @RequestParam("id") Long[] ids) {
        Arrays.stream(ids).forEach(roleService::deleteById);
        return Result.success();
    }

}
