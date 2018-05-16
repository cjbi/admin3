package tech.wetech.admin.service.system.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tech.wetech.admin.common.base.PageResultSet;
import tech.wetech.admin.mapper.system.RoleMapper;
import tech.wetech.admin.model.system.Resource;
import tech.wetech.admin.model.system.Role;
import tech.wetech.admin.model.system.RoleDto;
import tech.wetech.admin.service.system.ResourceService;
import tech.wetech.admin.service.system.RoleService;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

import java.util.*;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ResourceService resourceService;

    @Override
    public PageResultSet<RoleDto> findByPage(Role role) {
        PageHelper.offsetPage(role.getOffset(), role.getLimit());
        if(!StringUtils.isEmpty(role.getOrderBy())) {
            PageHelper.orderBy(role.getOrderBy());
        }
        Weekend<Role> weekend = Weekend.of(Role.class);
        WeekendCriteria<Role, Object> criteria = weekend.weekendCriteria();
        if (!StringUtils.isEmpty(role.getSearch())) {
            criteria.andLike(Role::getRole, "%" + role.getSearch() + "%")
                    .orLike(Role::getDescription, "%" + role.getSearch() + "%");
        }
        PageResultSet<RoleDto> resultSet = new PageResultSet<>();
        List<RoleDto> dtoList = new ArrayList<>();
        roleMapper.selectByExample(weekend).forEach(r -> {
            RoleDto dto = new RoleDto(r);
            dto.setResourceNames(getResourceNames(r.getResourceIdList()));
            dtoList.add(dto);
        });
        long count = roleMapper.selectCountByExample(weekend);
        resultSet.setRows(dtoList);
        resultSet.setTotal(count);
        return resultSet;
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
        return roleMapper.selectAll();
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
        Set<Long> resourceIds = new HashSet<>();
        for (Long roleId : roleIds) {
            Role role = findOne(roleId);
            if (role != null) {
                resourceIds.addAll(role.getResourceIdList());
            }
        }
        return resourceService.findPermissions(resourceIds);
    }
}
