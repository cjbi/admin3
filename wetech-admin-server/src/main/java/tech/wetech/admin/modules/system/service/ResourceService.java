package tech.wetech.admin.modules.system.service;

import tech.wetech.admin.modules.base.service.IService;
import tech.wetech.admin.modules.system.dto.ResourceDTO;
import tech.wetech.admin.modules.system.po.Resource;

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
    Set<String> findPermissions(Set<Long> resourceIds);

    /**
     * 根据用户权限得到菜单
     *
     * @param permissions
     * @return
     */
    List<ResourceDTO> findMenus(Set<String> permissions);

    List<Resource> findOrderByPriority();

}
