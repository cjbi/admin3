package tech.wetech.admin.service;

import tech.wetech.admin.model.dto.RoleDTO;
import tech.wetech.admin.model.entity.Role;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RoleService {

    /**
     * 根据角色编号得到角色标识符列表
     *
     * @param roleIds
     * @return
     */
    Set<String> queryRoles(Long... roleIds);

    /**
     * 根据角色编号得到角色名称
     * @param roleIds
     * @return map => key:角色标识符，value:角色名称
     */
    Map<String, String> queryRoleNames(Long... roleIds);

    /**
     * 根据角色编号得到权限字符串列表
     *
     * @param roleIds
     * @return
     */
    Set<String> queryPermissions(Long... roleIds);

    List<RoleDTO> queryAllRole();

    void create(Role role);

    void updateNotNull(Role role);

    void deleteById(Long id);
}
