package tech.wetech.admin.service.impl;

import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tech.wetech.admin.service.BaseService;
import tech.wetech.admin.model.dto.ResourceDTO;
import tech.wetech.admin.model.enumeration.ResourceType;
import tech.wetech.admin.mapper.ResourceMapper;
import tech.wetech.admin.model.entity.Resource;
import tech.wetech.admin.service.ResourceService;
import tech.wetech.mybatis.example.Example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ResourceServiceImpl extends BaseService<Resource> implements ResourceService {

    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    @Transactional
    public void createResource(Resource resource) {
        Resource parent = resourceMapper.createCriteria().andEqualTo(Resource::getParentId, resource.getParentId()).selectOne();
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
    public Set<String> queryPermissions(Set<Long> resourceIds) {
        Set<String> permissions = new HashSet<>();
        for (Long resourceId : resourceIds) {
            Resource resource = resourceMapper.createCriteria().andEqualTo(Resource::getId, resourceId).selectOne();
            if (resource != null && !StringUtils.isEmpty(resource.getPermission())) {
                permissions.add(resource.getPermission());
            }
        }
        return permissions;
    }

    @Override
    public List<ResourceDTO> queryMenus(Set<String> permissions) {
        Example example = Example.of(Resource.class);
        example.setOrderByClause("priority");
        List<Resource> allResources = resourceMapper.selectByExample(example);
        List<ResourceDTO> menus = new ArrayList<>();
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
            menus.add(new ResourceDTO(resource));
        }
        return menus;
    }

    @Override
    public List<Resource> queryOrderByPriority() {
        Example<Resource> example = Example.of(Resource.class);
        example.setOrderByClause("priority");
        return resourceMapper.selectByExample(example);
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
