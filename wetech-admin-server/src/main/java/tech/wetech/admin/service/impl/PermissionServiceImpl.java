package tech.wetech.admin.service.impl;

import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tech.wetech.admin.mapper.PermissionMapper;
import tech.wetech.admin.model.SystemContextHolder;
import tech.wetech.admin.model.constant.Constants;
import tech.wetech.admin.model.dto.PermissionTreeDTO;
import tech.wetech.admin.model.entity.Permission;
import tech.wetech.admin.service.PermissionService;
import tech.wetech.mybatis.domain.Sort;
import tech.wetech.mybatis.example.Example;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    @Transactional
    public void createPermission(Permission permission) {
        if (permission.getParentId() == Constants.PERMISSION_ROOT_ID) {
            permission.setParentIds("0/");
        } else {
            Permission parent = permissionMapper.createCriteria().andEqualTo(Permission::getId, permission.getParentId()).selectOne();
            permission.setParentIds(parent.makeSelfAsParentIds());
        }
        permission.setStatus(1);
        permissionMapper.insertSelective(permission);
    }

    @Override
    public Set<String> queryPermissionTree(Long... permissionIds) {
        Set<String> permissions = new HashSet<>();
        List<Permission> permissionsList = permissionMapper.createCriteria().andIn(Permission::getId, Arrays.asList(permissionIds)).selectList();
        for (Permission permission : permissionsList) {
            if (StringUtils.isEmpty(permission.getPermission())) {
                continue;
            }
            permissions.add(permission.getPermission());
        }
        return permissions;
    }

    @Override
    public List<PermissionTreeDTO> queryMenus(Set<String> permissions) {
        Example example = Example.of(Permission.class);
        example.setOrderByClause("sort");
        List<Permission> allPermissions = permissionMapper.selectByExample(example);
        List<PermissionTreeDTO> menus = new ArrayList<>();
        for (Permission permission : allPermissions) {
            if (permission.getType() != 1) {
                continue;
            }
            if (!hasPermission(permissions, permission)) {
                continue;
            }
            menus.add(new PermissionTreeDTO(permission));
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
    public List<PermissionTreeDTO> queryPermissionTree() {
        Example<Permission> example = Example.of(Permission.class);
        example.setSort(Sort.by("sort"));
        List<Permission> permissions = permissionMapper.selectByExample(example).stream()
                .collect(Collectors.toList());
        SystemContextHolder.putThreadCache("permissions", permissions);
        return getPermissionTree(permissions, Constants.PERMISSION_ROOT_ID);
    }

    @Override
    public void updateNotNull(Permission permission) {
        permissionMapper.updateByPrimaryKeySelective(permission);
    }

    @Override
    public void deleteById(Long id) {
        permissionMapper.deleteByPrimaryKey(id);
    }

    private List<PermissionTreeDTO> getPermissionTree(List<Permission> list, Long parentId) {
        List<PermissionTreeDTO> permissionTree = list.stream()
                .filter(p -> p.getParentId().equals(parentId))
                .map(PermissionTreeDTO::new)
                .collect(Collectors.toList());
        if (permissionTree.isEmpty()) {
            return Collections.emptyList();
        }
        for (PermissionTreeDTO permission : permissionTree) {
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
