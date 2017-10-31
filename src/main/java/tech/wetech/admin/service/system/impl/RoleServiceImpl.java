package tech.wetech.admin.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tech.wetech.admin.mapper.system.RoleMapper;
import tech.wetech.admin.model.system.Resource;
import tech.wetech.admin.model.system.Role;
import tech.wetech.admin.model.system.RoleExample;
import tech.wetech.admin.service.system.ResourceService;
import tech.wetech.admin.service.system.RoleService;
import tech.wetech.admin.web.dto.Page;
import tech.wetech.admin.web.dto.system.RoleDto;

import java.util.*;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ResourceService resourceService;

    @Override
    public Page listByPage(Page page) {
        RoleExample example = new RoleExample();
        example.setOffset(page.getStart());
        example.setLimit(page.getLength());
        List<Role> roleList = roleMapper.selectByExample(example);
        long count = roleMapper.countByExample(example);
        roleMapper.countByExample(example);
        List<RoleDto> dtoList = new ArrayList<>();
        for (Role role : roleList) {
            RoleDto dto = new RoleDto(role);
            dto.setResourceNames(getResourceNames(role.getResourceIdList()));
            dtoList.add(dto);
        }
        page.setResult(dtoList);
        page.setTotal(count);
        return page;
    }

    private String getResourceNames(Collection<Long> resourceIds) {
        if (CollectionUtils.isEmpty(resourceIds)) {
            return "";
        }
        StringBuilder s = new StringBuilder();
        for (Long resourceId : resourceIds) {
            Resource resource = resourceService.findOne(resourceId);
            if (resource == null) {
                return "";
            }
            s.append(resource.getName());
            s.append(",");
        }
        if (s.length() > 0) {
            s.deleteCharAt(s.length() - 1);
        }
        return s.toString();
    }

    @Override
    public int createRole(Role role) {
        return roleMapper.insert(role);
    }

    @Override
    public int updateRole(Role role) {
        return roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        roleMapper.deleteByPrimaryKey(roleId);
    }

    @Override
    public Role findOne(Long roleId) {
        return roleMapper.selectByPrimaryKey(roleId);
    }

    @Override
    public List<Role> findAll() {
        return roleMapper.selectByExample(new RoleExample());
    }

    @Override
    public Set<String> findRoles(Long... roleIds) {
        Set<String> roles = new HashSet<>();
        for (Long roleId : roleIds) {
            Role role = findOne(roleId);
            if (role != null) {
                roles.add(role.getRole());
            }
        }
        return roles;
    }

    @Override
    public Set<String> findPermissions(Long[] roleIds) {
        Set<Long> resourceIds = new HashSet<Long>();
        for (Long roleId : roleIds) {
            Role role = findOne(roleId);
            if (role != null) {
                resourceIds.addAll(role.getResourceIdList());
            }
        }
        return resourceService.findPermissions(resourceIds);
    }
}
