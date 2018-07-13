package tech.wetech.admin.service.system.impl;

import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tech.wetech.admin.mapper.system.ResourceMapper;
import tech.wetech.admin.model.system.Resource;
import tech.wetech.admin.service.system.ResourceService;
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
    public int createResource(Resource resource) {
        Resource parent = findOne(resource.getParentId());
        resource.setParentIds(parent.makeSelfAsParentIds());
        resource.setAvailable(true);
        if (resource.getType() == Resource.ResourceType.menu) {
            if (StringUtils.isEmpty(resource.getUrl())) {
                resource.setUrl("#");
            }
        }
        return resourceMapper.insertSelective(resource);
    }

    @Override
    @Transactional
    public int updateResource(Resource resource) {
        return resourceMapper.updateByPrimaryKeySelective(resource);
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
    public List<Resource> find(Weekend weekend) {
        return resourceMapper.selectByExample(weekend);
    }

    @Override
    public List<Resource> findAll() {
        return resourceMapper.selectAll();
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
    public List<Resource> findMenus(Set<String> permissions) {
        Weekend weekend = Weekend.of(Resource.class);
        weekend.setOrderByClause("priority");
        List<Resource> allResources = resourceMapper.selectByExample(weekend);
        List<Resource> menus = new ArrayList<>();
        for (Resource resource : allResources) {
            if (resource.isRootNode()) {
                continue;
            }
            if (resource.getType() != Resource.ResourceType.menu) {
                continue;
            }
            if (!hasPermission(permissions, resource)) {
                continue;
            }
            menus.add(resource);
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
