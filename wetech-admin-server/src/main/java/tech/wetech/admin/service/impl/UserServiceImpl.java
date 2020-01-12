package tech.wetech.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.wetech.admin.exception.BizException;
import tech.wetech.admin.mapper.UserMapper;
import tech.wetech.admin.model.PageWrapper;
import tech.wetech.admin.model.dto.LoginDTO;
import tech.wetech.admin.model.dto.UserInfoDTO;
import tech.wetech.admin.model.dto.UserPageDTO;
import tech.wetech.admin.model.entity.User;
import tech.wetech.admin.model.enumeration.CommonResultStatus;
import tech.wetech.admin.model.query.UserQuery;
import tech.wetech.admin.service.BaseService;
import tech.wetech.admin.service.PasswordHelper;
import tech.wetech.admin.service.RoleService;
import tech.wetech.admin.service.UserService;
import tech.wetech.admin.shiro.JwtUtil;
import tech.wetech.mybatis.ThreadContext;
import tech.wetech.mybatis.domain.Page;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            throw new BizException(CommonResultStatus.FAILED_USER_ALREADY_EXIST);
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
    public Set<String> queryRoles(String username) {
        User user = userMapper.createCriteria().andEqualTo(User::getUsername, username).selectOne();
        if (user == null) {
            return Collections.EMPTY_SET;
        }
        return roleService.queryRoles(
            Arrays.asList(user.getRoleIds().split(",")).stream().map(Long::valueOf).collect(Collectors.toList()).toArray(new Long[0])
        );
    }

    @Override
    public Set<String> queryPermissions(String username) {
        User user = userMapper.createCriteria().andEqualTo(User::getUsername, username).selectOne();
        if (user == null) {
            return Collections.EMPTY_SET;
        }
        return roleService.queryPermissions(
            Arrays.asList(user.getRoleIds().split(",")).stream().map(Long::valueOf).collect(Collectors.toList()).toArray(new Long[0])
        );
    }

    @Override
    public UserInfoDTO queryUserInfo(String username) {
        User user = userMapper.createCriteria()
            .andEqualTo(User::getUsername, username)
            .selectOne();
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setId(user.getId());
        userInfoDTO.setUsername(user.getUsername());
        userInfoDTO.setOrganizationId(user.getOrganizationId());
        userInfoDTO.setRoleIds(user.getRoleIds());
        return userInfoDTO;
    }

    @Override
    public UserInfoDTO login(LoginDTO loginDTO) {
        User user = userMapper.createCriteria().andEqualTo(User::getUsername, loginDTO.getUsername()).selectOne();
        if (user == null) {
            throw new BizException(CommonResultStatus.LOGIN_ERROR, "用户不存在");
        }
        if (!passwordHelper.verifyPassword(user, loginDTO.getPassword())) {
            throw new BizException(CommonResultStatus.LOGIN_ERROR, "密码错误");
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setId(user.getId());
        userInfoDTO.setUsername(user.getUsername());
        userInfoDTO.setOrganizationId(user.getOrganizationId());
        userInfoDTO.setRoleIds(user.getRoleIds());
        userInfoDTO.setToken(JwtUtil.sign(user.getUsername(), String.valueOf(System.currentTimeMillis())));
        return userInfoDTO;
    }

    @Override
    public PageWrapper<UserPageDTO> queryUserList(UserQuery userQuery) {
        ThreadContext.setPage(userQuery.getPageNo(), userQuery.getPageSize(), true);
        Page<User> users = (Page<User>) userMapper.selectAll();
        List<UserPageDTO> list = new ArrayList<>();
        for (User user : users) {
            UserPageDTO userDTO = new UserPageDTO();
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setRoleIds(user.getRoleIds());
            userDTO.setRoleNames(getRoleNames(user.getRoleIds()));
            userDTO.setLocked(user.getLocked());
            list.add(userDTO);
        }
        return new PageWrapper<>(list, users.getTotal(), users.getPageNumber(), users.getPageSize());
    }

    private String getRoleNames(String roleIdStr) {
        Long[] roleIds = Stream.of(roleIdStr.split(","))
            .map(Long::valueOf)
            .collect(Collectors.toSet())
            .toArray(new Long[]{});

        return roleService.queryRoles(roleIds).stream()
            .collect(Collectors.joining(","));
    }

}
