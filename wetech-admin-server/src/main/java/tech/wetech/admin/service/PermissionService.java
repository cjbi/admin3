package tech.wetech.admin.service;


import tech.wetech.admin.model.dto.PermissionDTO;
import tech.wetech.admin.model.entity.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionService extends IService<Permission> {

    void createPermission(Permission permission);

    /**
     * 得到资源对应的权限字符串
     *
     * @param permissionIds
     * @return
     */
    Set<String> queryPermissionTree(Long... permissionIds);

    /**
     * 根据用户权限得到菜单
     *
     * @param permissions
     * @return
     */
    List<PermissionDTO> queryMenus(Set<String> permissions);

    List<Permission> queryPermissionsByOrder();

    List<PermissionDTO> queryPermissionTree();

}
