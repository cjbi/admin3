package tech.wetech.admin.modules.system.service.impl;

import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tech.wetech.admin.modules.system.dto.ResourceDto;
import tech.wetech.admin.modules.system.mapper.ResourceMapper;
import tech.wetech.admin.modules.system.po.Resource;
import tech.wetech.admin.modules.system.enums.ResourceType;
import tech.wetech.admin.modules.system.service.ResourceService;
import tk.mybatis.mapper.weekend.Weekend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    @Transactional
    public void createResource(Resource resource) {
        Resource parent = findOne(resource.getParentId());
        resource.setParentIds(parent.makeSelfAsParentIds());
        resource.setAvailable(true);
        if (resource.getType() == ResourceType.MENU) {
            if (StringUtils.isEmpty(resource.getUrl())) {
                resource.setUrl("#");
            }
        }
        resourceMapper.insertSelective(resource);
    }

    @Override
    @Transactional
    public void updateResource(Resource resource) {
        resourceMapper.updateByPrimaryKeySelective(resource);
    }

    @Override
    @Transactional
    public void deleteResource(Long resourceId) {
        resourceMapper.deleteByPrimaryKey(resourceId);
    }

    @Override
    public Resource findOne(Long resourceId) {
        return resourceMapper.selectByPrimaryKey(resourceId);
    }

    @Override
    public List<ResourceDto> find(Weekend example) {
        List<ResourceDto> resourceDtoList = new ArrayList<>();
        resourceMapper.selectByExample(example).forEach(resource-> {
            resourceDtoList.add(new ResourceDto(resource));
        });
        return resourceDtoList;
    }

    @Override
    public List<ResourceDto> findAll() {
        List<ResourceDto> resourceDtoList = new ArrayList<>();
        resourceMapper.selectAll().forEach(resource-> {
            resourceDtoList.add(new ResourceDto(resource));
        });
        return resourceDtoList;
    }

    @Override
    public Set<String> findPermissions(Set<Long> resourceIds) {
        Set<String> permissions = new HashSet<>();
        for (Long resourceId : resourceIds) {
            Resource resource = findOne(resourceId);
            if (resource != null && !StringUtils.isEmpty(resource.getPermission())) {
                permissions.add(resource.getPermission());
            }
        }
        return permissions;
    }

    @Override
    public List<ResourceDto> findMenus(Set<String> permissions) {
        Weekend weekend = Weekend.of(Resource.class);
        weekend.setOrderByClause("priority");
        List<Resource> allResources = resourceMapper.selectByExample(weekend);
        List<ResourceDto> menus = new ArrayList<>();
        for (Resource resource : allResources) {
            if (resource.isRootNode()) {
                continue;
            }
            if (resource.getType() != ResourceType.MENU) {
                continue;
            }
            if (!hasPermission(permissions, resource)) {
                continue;
            }
            menus.add(new ResourceDto(resource));
        }
        return menus;
    }

    private boolean hasPermission(Set<String> permissions, Resource resource) {
        if (StringUtils.isEmpty(resource.getPermission())) {
            return true;
        }
        for (String permission : permissions) {
            WildcardPermission p1 = new WildcardPermission(permission);
            WildcardPermission p2 = new WildcardPermission(resource.getPermission());
            if (p1.implies(p2) || p2.implies(p1)) {
                return true;
            }
        }
        return false;
    }

}
