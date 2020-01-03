package tech.wetech.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.wetech.admin.exception.BizException;
import tech.wetech.admin.utils.ResultCodeEnum;
import tech.wetech.admin.service.BaseService;
import tech.wetech.admin.mapper.UserMapper;
import tech.wetech.admin.model.entity.User;
import tech.wetech.admin.service.PasswordHelper;
import tech.wetech.admin.service.RoleService;
import tech.wetech.admin.service.UserService;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends BaseService<User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordHelper passwordHelper;

    @Override
    @Transactional
    public void createUser(User user) {
        User u = userMapper.createCriteria().andEqualTo(User::getUsername, user.getUsername()).selectOne();
        if (u != null) {
            throw new BizException(ResultCodeEnum.FAILED_USER_ALREADY_EXIST);
        }
        // 加密密码
        passwordHelper.encryptPassword(user);
        userMapper.insertSelective(user);
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
    public Set<String> findRoles(String username) {
        User user = userMapper.createCriteria().andEqualTo(User::getUsername, username).selectOne();
        if (user == null) {
            return Collections.EMPTY_SET;
        }
        return roleService.queryRoles(
                Arrays.asList(user.getRoleIds().split(",")).stream().map(Long::valueOf).collect(Collectors.toList()).toArray(new Long[0])
        );
    }

    @Override
    public Set<String> findPermissions(String username) {
        User user = userMapper.createCriteria().andEqualTo(User::getUsername, username).selectOne();
        if (user == null) {
            return Collections.EMPTY_SET;
        }
        return roleService.queryPermissions(
                Arrays.asList(user.getRoleIds().split(",")).stream().map(Long::valueOf).collect(Collectors.toList()).toArray(new Long[0])
        );
    }
}
