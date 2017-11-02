package tech.wetech.admin.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tech.wetech.admin.mapper.system.UserMapper;
import tech.wetech.admin.model.system.*;
import tech.wetech.admin.service.system.OrganizationService;
import tech.wetech.admin.service.system.PasswordHelper;
import tech.wetech.admin.service.system.RoleService;
import tech.wetech.admin.service.system.UserService;
import tech.wetech.admin.web.dto.PageData;
import tech.wetech.admin.web.dto.system.UserDto;
import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private PasswordHelper passwordHelper;

    @Override
    public List<User> selectByExample(UserExample example) {
        return userMapper.selectByExample(example);
    }

    @Override
    public PageData list(PageData pageData) {
        UserExample example = new UserExample();
        example.setOffset(pageData.getStart());
        example.setLimit(pageData.getLength());
        if (!StringUtils.isEmpty(pageData.getKeywords())) {
            example.or().andUsernameLike("%" + pageData.getKeywords() + "%");
        }
        long count = userMapper.countByExample(example);
        List<User> userList = userMapper.selectByExample(example);
        List<UserDto> dtoList = new ArrayList<>();
        for (User user : userList) {
            UserDto dto = new UserDto(user);
            dto.setOrganizationName(getOrganizationName(dto.getOrganizationId()));
            dto.setRoleNames(getRoleNames(dto.getRoleIdList()));
            dtoList.add(dto);
        }
        pageData.setResult(dtoList);
        pageData.setTotal(count);
        return pageData;
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
            throw new ServiceException("该用户已存在");
        }
        // 加密密码
        passwordHelper.encryptPassword(user);
        return userMapper.insert(user);
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
        return userMapper.selectByExample(new UserExample());
    }

    @Override
    public User findByUsername(String username) {
        UserExample example = new UserExample();
        example.or().andUsernameEqualTo(username);
        List<User> list = userMapper.selectByExample(example);
        return !list.isEmpty() ? list.get(0) : null;
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
