package tech.wetech.admin.modules.system.service.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tech.wetech.admin.core.utils.PageResultSet;
import tech.wetech.admin.core.utils.ResultCodeEnum;
import tech.wetech.admin.core.exception.BizException;
import tech.wetech.admin.modules.system.mapper.UserMapper;
import tech.wetech.admin.modules.system.po.Group;
import tech.wetech.admin.modules.system.po.Organization;
import tech.wetech.admin.modules.system.po.Role;
import tech.wetech.admin.modules.system.po.User;
import tech.wetech.admin.modules.system.dto.UserDto;
import tech.wetech.admin.modules.system.query.UserQuery;
import tech.wetech.admin.modules.system.service.*;
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
    private GroupService groupService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private PasswordHelper passwordHelper;

    @Override
    public PageResultSet<UserDto> findByPage(UserQuery userQuery) {

        if(!StringUtils.isEmpty(userQuery.getOrderBy())) {
            PageHelper.orderBy(userQuery.getOrderBy());
        }

        Weekend<User> example = Weekend.of(User.class);
        WeekendCriteria<User, Object> criteria = example.weekendCriteria();

        if (!StringUtils.isEmpty(userQuery.getUsername())) {
            criteria.andLike(User::getUsername, "%" + userQuery.getUsername() + "%");
        }
        if(userQuery.getLocked() != null) {
            criteria.andEqualTo(User::getLocked,userQuery.getLocked());
        }

        List<UserDto> dtoList = new ArrayList<>();

        PageHelper.offsetPage(userQuery.getOffset(), userQuery.getLimit());
        userMapper.selectByExample(example).forEach(u -> {
            UserDto dto = new UserDto(u);
            dto.setOrganizationName(getOrganizationName(Long.valueOf(dto.getOrganizationId())));
            dto.setRoleNames(getRoleNames(dto.getRoleIdList()));
            dto.setGroupNames(getGroupNames(dto.getGroupIdList()));
            dtoList.add(dto);
        });

        long total = userMapper.selectCountByExample(example);
        PageResultSet<UserDto> resultSet = new PageResultSet<>();
        resultSet.setRows(dtoList);
        resultSet.setTotal(total);
        return resultSet;
    }

    private String getGroupNames(Collection<Long> groupIds) {
        if (CollectionUtils.isEmpty(groupIds)) {
            return "";
        }

        StringBuilder s = new StringBuilder();
        for (Long groupId : groupIds) {
            Group role = groupService.findOne(groupId);
            if (role != null) {
                s.append(role.getName());
                s.append(",");
            }
        }

        if (s.length() > 0) {
            s.deleteCharAt(s.length() - 1);
        }

        return s.toString();
    }


    private String getRoleNames(Collection<Long> groupIds) {
        if (CollectionUtils.isEmpty(groupIds)) {
            return "";
        }

        StringBuilder s = new StringBuilder();
        for (Long roleId : groupIds) {
            Role role = roleService.findOne(roleId);
            if (role != null) {
                s.append(role.getDescription());
                s.append(",");
            }
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
    @Transactional
    public void createUser(User user) {
        User u = findByUsername(user.getUsername());
        if (u != null) {
            throw new BizException(ResultCodeEnum.FAILED_USER_ALREADY_EXIST);
        }
        // 加密密码
        passwordHelper.encryptPassword(user);
        userMapper.insertSelective(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        userMapper.deleteByPrimaryKey(userId);
    }

    @Override
    @Transactional
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
