package tech.wetech.admin.modules.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tech.wetech.admin.core.utils.PageResultSet;
import tech.wetech.admin.modules.system.mapper.RoleMapper;
import tech.wetech.admin.modules.system.po.Resource;
import tech.wetech.admin.modules.system.po.Role;
import tech.wetech.admin.modules.system.dto.RoleDto;
import tech.wetech.admin.modules.system.query.RoleQuery;
import tech.wetech.admin.modules.system.service.ResourceService;
import tech.wetech.admin.modules.system.service.RoleService;
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
    public PageResultSet<RoleDto> findByPage(RoleQuery roleQuery) {
        PageHelper.offsetPage(roleQuery.getOffset(), roleQuery.getLimit());
        if(!StringUtils.isEmpty(roleQuery.getOrderBy())) {
            PageHelper.orderBy(roleQuery.getOrderBy());
        }
        Weekend<Role> example = Weekend.of(Role.class);
        WeekendCriteria<Role, Object> criteria = example.weekendCriteria();
        if(!StringUtils.isEmpty(roleQuery.getRole())) {
            criteria.andLike(Role::getRole,"%" + roleQuery.getRole() + "%");
        }
        if(!StringUtils.isEmpty(roleQuery.getDescription())) {
            criteria.andLike(Role::getDescription,"%" + roleQuery.getDescription() + "%");
        }
        PageResultSet<RoleDto> resultSet = new PageResultSet<>();
        List<RoleDto> dtoList = new ArrayList<>();
        roleMapper.selectByExample(example).forEach(r -> {
            RoleDto dto = new RoleDto(r);
            dto.setResourceNames(getResourceNames(r.getResourceIdList()));
            dtoList.add(dto);
        });
        long total = roleMapper.selectCountByExample(example);
        resultSet.setRows(dtoList);
        resultSet.setTotal(total);
        return resultSet;
    }

    private String getResourceNames(Collection<Long> resourceIds) {
        if (CollectionUtils.isEmpty(resourceIds)) {
            return "";
        }
        StringBuilder s = new StringBuilder();
        for (Long resourceId : resourceIds) {
            Resource resource = resourceService.findOne(resourceId);
            if (resource != null) {
                s.append(resource.getName());
                s.append(",");
            }
        }
        if (s.length() > 0) {
            s.deleteCharAt(s.length() - 1);
        }
        return s.toString();
    }

    @Override
    @Transactional
    public void createRole(Role role) {
        roleMapper.insertSelective(role);
    }

    @Override
    @Transactional
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    @Transactional
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
