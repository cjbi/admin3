package tech.wetech.admin.service.system;

import tech.wetech.admin.common.base.Page;
import tech.wetech.admin.common.base.PageResultSet;
import tech.wetech.admin.model.system.Role;
import tech.wetech.admin.web.dto.DataTableModel;
import tech.wetech.admin.web.dto.system.RoleDto;

import java.util.List;
import java.util.Set;

public interface RoleService {

    PageResultSet<RoleDto> findByPage(Page page);

    int createRole(Role role);

    int updateRole(Role role);

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
