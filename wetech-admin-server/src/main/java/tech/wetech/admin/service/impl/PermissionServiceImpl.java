package tech.wetech.admin.service.impl;

import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tech.wetech.admin.mapper.PermissionMapper;
import tech.wetech.admin.model.constant.Constants;
import tech.wetech.admin.model.dto.PermissionDTO;
import tech.wetech.admin.model.entity.Permission;
import tech.wetech.admin.service.BaseService;
import tech.wetech.admin.service.PermissionService;
import tech.wetech.mybatis.example.Example;
import tech.wetech.mybatis.example.Sort;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl extends BaseService<Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    @Transactional
    public void createPermission(Permission permission) {
        Permission parent = permissionMapper.createCriteria().andEqualTo(Permission::getParentId, permission.getParentId()).selectOne();
        permission.setParentIds(parent.makeSelfAsParentIds());
        permission.setStatus(1);
        permissionMapper.insertSelective(permission);
    }

    @Override
    public Set<String> queryPermissionTree(Set<Long> resourceIds) {
        Set<String> permissions = new HashSet<>();
        for (Long resourceId : resourceIds) {
            Permission permission = permissionMapper.createCriteria().andEqualTo(Permission::getId, resourceId).selectOne();
            if (permission != null && !StringUtils.isEmpty(permission.getPermission())) {
                permissions.add(permission.getPermission());
            }
        }
        return permissions;
    }

    @Override
    public List<PermissionDTO> queryMenus(Set<String> permissions) {
        Example example = Example.of(Permission.class);
        example.setOrderByClause("sort");
        List<Permission> allPermissions = permissionMapper.selectByExample(example);
        List<PermissionDTO> menus = new ArrayList<>();
        for (Permission permission : allPermissions) {
            if (permission.getType() != 1) {
                continue;
            }
            if (!hasPermission(permissions, permission)) {
                continue;
            }
            menus.add(new PermissionDTO(permission));
        }
        return menus;
    }

    @Override
    public List<Permission> queryPermissionsByOrder() {
        Example<Permission> example = Example.of(Permission.class);
        example.setOrderByClause("sort");
        return permissionMapper.selectByExample(example);
    }

    @Override
    public List<PermissionDTO> queryPermissionTree() {
        Example<Permission> example = Example.of(Permission.class);
        example.setSort(new Sort("sort"));

        List<Permission> permissions = permissionMapper.selectByExample(example).stream()
            .collect(Collectors.toList());

        return getPermissionTree(permissions, Constants.MENU_ROOT_ID);
    }

    private List<PermissionDTO> getPermissionTree(List<Permission> list, Long parentId) {
        List<PermissionDTO> permissionTree = list.stream()
            .filter(p -> p.getParentId().equals(parentId))
            .map(PermissionDTO::new)
            .collect(Collectors.toList());
        if(permissionTree.isEmpty()) {
            return null;
        }
        for (PermissionDTO permission : permissionTree) {
            permission.setChildren(getPermissionTree(list, permission.getId()));
        }
        return permissionTree;
    }

    private boolean hasPermission(Set<String> permissions, Permission resource) {
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
