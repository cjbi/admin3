package tech.wetech.admin.modules.system.service;

import tech.wetech.admin.core.utils.PageResultSet;
import tech.wetech.admin.modules.system.po.Role;
import tech.wetech.admin.modules.system.dto.RoleDto;
import tech.wetech.admin.modules.system.query.RoleQuery;

import java.util.List;
import java.util.Set;

public interface RoleService {

    PageResultSet<RoleDto> findByPage(RoleQuery roleQuery);

    void createRole(Role role);

    void updateRole(Role role);

    void deleteRole(Long roleId);

    Role findOne(Long roleId);

    List<Role> findAll();

    /**
     * 根据角色编号得到角色标识符列表
     * @param roleIds
     * @return
     */
    Set<String> findRoles(Long... roleIds);

    /**
     * 根据角色编号得到权限字符串列表
     * @param roleIds
     * @return
     */
    Set<String> findPermissions(Long[] roleIds);
}
