package tech.wetech.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.wetech.admin.mapper.RoleMapper;
import tech.wetech.admin.model.dto.RoleDTO;
import tech.wetech.admin.model.entity.Role;
import tech.wetech.admin.service.BaseService;
import tech.wetech.admin.service.PermissionService;
import tech.wetech.admin.service.RoleService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RoleServiceImpl extends BaseService<Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionService permissionService;

    @Override
    public Set<String> queryRoles(Long... roleIds) {
        Set<String> roles = new HashSet<>();
        List<Role> roleList = roleMapper.createCriteria().andIn(Role::getId, Arrays.asList(roleIds)).selectList();
        for (Role role : roleList) {
            roles.add(role.getRole());
        }
        return roles;
    }

    @Override
    public Map<String, String> queryRoleNames(Long... roleIds) {
        Map<String, String> roleMap = new HashMap<>();
        List<Role> roleList = roleMapper.createCriteria().andIn(Role::getId, Arrays.asList(roleIds)).selectList();
        for (Role role : roleList) {
            roleMap.put(role.getRole(), role.getName());
        }
        return roleMap;
    }

    @Override
    public Set<String> queryPermissions(Long... roleIds) {
        return permissionService.queryPermissionTree(
            roleMapper.createCriteria().andIn(Role::getId, Arrays.asList(roleIds)).selectList().stream().flatMap(r ->
                Stream.of(r.getPermissionIds().split(","))
            ).map(Long::valueOf).collect(Collectors.toSet()).toArray(new Long[]{})
        );
    }

    @Override
    public List<RoleDTO> queryAllRole() {
        return roleMapper.selectAll().stream()
            .map(RoleDTO::new)
            .collect(Collectors.toList());
    }

}
