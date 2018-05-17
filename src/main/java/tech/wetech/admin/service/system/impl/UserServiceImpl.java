package tech.wetech.admin.service.system.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tech.wetech.admin.common.base.PageResultSet;
import tech.wetech.admin.common.base.ResultCodeEnum;
import tech.wetech.admin.common.exception.BizException;
import tech.wetech.admin.mapper.system.UserMapper;
import tech.wetech.admin.model.system.*;
import tech.wetech.admin.service.system.OrganizationService;
import tech.wetech.admin.service.system.PasswordHelper;
import tech.wetech.admin.service.system.RoleService;
import tech.wetech.admin.service.system.UserService;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private PasswordHelper passwordHelper;

    @Override
    public PageResultSet<UserDto> findByPage(User user) {
        PageHelper.offsetPage(user.getOffset(), user.getLimit());
        if(!StringUtils.isEmpty(user.getOrderBy())) {
            PageHelper.orderBy(user.getOrderBy());
        }
        Weekend<User> weekend = Weekend.of(User.class);
        WeekendCriteria<User, Object> criteria = weekend.weekendCriteria();
        if (!StringUtils.isEmpty(user.getSearch())) {
            criteria.andLike(User::getUsername, "%" + user.getSearch() + "%");
        }
        List<UserDto> dtoList = new ArrayList<>();
        userMapper.selectByExample(weekend).forEach(u -> {
            UserDto dto = new UserDto(u);
            dto.setOrganizationName(getOrganizationName(Long.valueOf(dto.getOrganizationId())));
            dto.setRoleNames(getRoleNames(dto.getRoleIdList()));
            dtoList.add(dto);
        });

        long count = userMapper.selectCountByExample(weekend);
        PageResultSet<UserDto> resultSet = new PageResultSet<>();
        resultSet.setRows(dtoList);
        resultSet.setTotal(count);
        return resultSet;
    }

    private String getRoleNames(Collection<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return "";
        }

        StringBuilder s = new StringBuilder();
        for (Long roleId : roleIds) {
            Role role = roleService.findOne(roleId);
            if (role == null) {
                return "";
            }
            s.append(role.getDescription());
            s.append(",");
        }

        if (s.length() > 0) {
            s.deleteCharAt(s.length() - 1);
        }

        return s.toString();
    }

    private String getOrganizationName(Long organizationId) {
        Organization organization = organizationService.findOne(organizationId);
        if (organization == null) {
            return "";
        }
        return organization.getName();
    }

    @Override
    public int createUser(User user) {
        User u = findByUsername(user.getUsername());
        if (u != null) {
            throw new BizException(ResultCodeEnum.FailedUserAlreadyExist);
        }
        // 加密密码
        passwordHelper.encryptPassword(user);
        return userMapper.insertSelective(user);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public void changePassword(Long userId, String newPassword) {
        User user = userMapper.selectByPrimaryKey(userId);
        user.setPassword(newPassword);
        passwordHelper.encryptPassword(user);
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    public User findOne(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<User> findAll() {
        return userMapper.selectAll();
    }

    @Override
    public User findByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        userMapper.selectOne(user);
        return userMapper.selectOne(user);
    }

    @Override
    public Set<String> findRoles(String username) {
        User user = findByUsername(username);
        if (user == null) {
            return Collections.EMPTY_SET;
        }
        return roleService.findRoles(user.getRoleIdList().toArray(new Long[0]));
    }

    @Override
    public Set<String> findPermissions(String username) {
        User user = findByUsername(username);
        if (user == null) {
            return Collections.EMPTY_SET;
        }
        return roleService.findPermissions(user.getRoleIdList().toArray(new Long[0]));
    }
}
