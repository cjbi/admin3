package tech.wetech.admin.modules.system.service;

import tech.wetech.admin.modules.system.dto.ResourceDto;
import tech.wetech.admin.modules.system.po.Resource;
import tk.mybatis.mapper.weekend.Weekend;

import java.util.List;
import java.util.Set;

public interface ResourceService {

    void createResource(Resource resource);

    void updateResource(Resource resource);

    void deleteResource(Long resourceId);

    Resource findOne(Long resourceId);

    List<ResourceDto> find(Weekend example);

    List<ResourceDto> findAll();

    /**
     * 得到资源对应的权限字符串
     * @param resourceIds
     * @return
     */
    Set<String> findPermissions(Set<Long> resourceIds);

    /**
     * 根据用户权限得到菜单
     * @param permissions
     * @return
     */
    List<ResourceDto> findMenus(Set<String> permissions);

}
