package tech.wetech.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.wetech.admin.service.BaseService;
import tech.wetech.admin.mapper.RoleMapper;
import tech.wetech.admin.model.entity.Role;
import tech.wetech.admin.service.ResourceService;
import tech.wetech.admin.service.RoleService;
import tech.wetech.mybatis.example.Example;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends BaseService<Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ResourceService resourceService;

    @Override
    public Set<String> queryRoles(Long... roleIds) {
        Set<String> roles = new HashSet<>();
        for (Long roleId : roleIds) {
            Role role = roleMapper.selectByPrimaryKey(roleId);
            if (role != null) {
                roles.add(role.getRole());
            }
        }
        return roles;
    }

    @Override
    public Set<String> queryPermissions(Long[] roleIds) {
        Example<Role> weekend = Example.of(Role.class);
        weekend.createCriteria().andIn(Role::getId, Arrays.asList(roleIds));
        return resourceService.queryPermissions(
                roleMapper.selectByExample(weekend).stream().flatMap(r ->
                        Arrays.asList(r.getResourceIds().split(",")).stream()
                ).map(Long::valueOf).collect(Collectors.toSet())
        );
    }
}
