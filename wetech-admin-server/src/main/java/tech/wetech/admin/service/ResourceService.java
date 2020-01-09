package tech.wetech.admin.service;


import tech.wetech.admin.model.dto.ResourceDTO;
import tech.wetech.admin.model.entity.Resource;

import java.util.List;
import java.util.Set;

public interface ResourceService extends IService<Resource> {

    void createResource(Resource resource);

    /**
     * 得到资源对应的权限字符串
     *
     * @param resourceIds
     * @return
     */
    Set<String> queryPermissions(Set<Long> resourceIds);

    /**
     * 根据用户权限得到菜单
     *
     * @param permissions
     * @return
     */
    List<ResourceDTO> queryMenus(Set<String> permissions);

    List<Resource> queryOrderByPriority();

}
