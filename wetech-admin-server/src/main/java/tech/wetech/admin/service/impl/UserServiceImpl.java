package tech.wetech.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.wetech.admin.exception.BusinessException;
import tech.wetech.admin.mapper.UserMapper;
import tech.wetech.admin.model.PageWrapper;
import tech.wetech.admin.model.dto.LoginDTO;
import tech.wetech.admin.model.dto.UserPageDTO;
import tech.wetech.admin.model.dto.UserTokenDTO;
import tech.wetech.admin.model.entity.User;
import tech.wetech.admin.model.enumeration.CommonResultStatus;
import tech.wetech.admin.model.query.PageQuery;
import tech.wetech.admin.model.query.UserQuery;
import tech.wetech.admin.service.BaseService;
import tech.wetech.admin.service.PasswordHelper;
import tech.wetech.admin.service.RoleService;
import tech.wetech.admin.service.UserService;
import tech.wetech.admin.shiro.JwtUtil;
import tech.wetech.mybatis.ThreadContext;
import tech.wetech.mybatis.domain.Page;
import tech.wetech.mybatis.example.Criteria;
import tech.wetech.mybatis.example.Example;
import tech.wetech.mybatis.example.Sort;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author cjbi
 */
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
            throw new BusinessException(CommonResultStatus.FAILED_USER_ALREADY_EXIST);
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
        return roleService.queryRoles(getRoleIds(user));
    }

    @Override
    public Set<String> queryPermissions(String username) {
        User user = userMapper.createCriteria().andEqualTo(User::getUsername, username).selectOne();
        if (user == null) {
            return Collections.EMPTY_SET;
        }
        return roleService.queryPermissions(getRoleIds(user));
    }

    @Override
    public User queryByUsername(String username) {
        return userMapper.createCriteria()
            .andEqualTo(User::getUsername, username)
            .selectOne();
    }

    @Override
    public UserTokenDTO login(LoginDTO loginDTO) {
        User user = userMapper.createCriteria().andEqualTo(User::getUsername, loginDTO.getUsername()).selectOne();
        if (user == null) {
            throw new BusinessException(CommonResultStatus.LOGIN_ERROR, "用户不存在");
        }
        if (!passwordHelper.verifyPassword(user, loginDTO.getPassword())) {
            throw new BusinessException(CommonResultStatus.LOGIN_ERROR, "密码错误");
        }
        UserTokenDTO userInfoDTO = new UserTokenDTO();
        userInfoDTO.setUsername(user.getUsername());
        userInfoDTO.setToken(JwtUtil.sign(user.getUsername(), String.valueOf(System.currentTimeMillis())));
        return userInfoDTO;
    }

    @Override
    public PageWrapper<UserPageDTO> queryUserPage(UserQuery userQuery) {
        Example<User> example = buildUserExample(userQuery);
        Page<User> users = (Page<User>) userMapper.selectByExample(example);
        List<UserPageDTO> list = new ArrayList<>();
        for (User user : users) {
            UserPageDTO userDTO = new UserPageDTO();
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setRoleIds(Arrays.asList(getRoleIds(user)));
            userDTO.setRoleNames(getRoleNames(user));
            userDTO.setLocked(user.getLocked());
            list.add(userDTO);
        }
        return new PageWrapper<>(list, users.getTotal(), users.getPageNumber(), users.getPageSize());
    }

    private Example<User> buildUserExample(UserQuery userQuery) {
        Example<User> example = Example.of(User.class);
        if (userQuery.getSortField() != null && userQuery.getSortOrder() != null) {
            if (userQuery.getSortOrder() == PageQuery.SortOrder.ascend) {
                example.setSort(new Sort(Sort.Direction.ASC, userQuery.getSortField()));
            }
            if (userQuery.getSortOrder() == PageQuery.SortOrder.descend) {
                example.setSort(new Sort(Sort.Direction.DESC, userQuery.getSortField()));
            }
        }
        Criteria<User> criteria = example.createCriteria();
        if (userQuery.getId() != null) {
            criteria.andEqualTo(User::getId, userQuery.getId());
        }
        if (userQuery.getUsername() != null) {
            criteria.andEqualTo(User::getUsername, userQuery.getUsername());
        }
        if (userQuery.getLocked() != null) {
            criteria.andEqualTo(User::getLocked, userQuery.getLocked());
        }
        ThreadContext.setPage(userQuery.getPageNo(), userQuery.getPageSize(), true);
        return example;
    }

    private List<String> getRoleNames(User user) {
        Map<String, String> roleMap = roleService.queryRoleNames(getRoleIds(user));
        return roleMap.entrySet().stream()
            .map(Map.Entry::getValue)
            .collect(Collectors.toList());
    }

    private Long[] getRoleIds(User user) {
        return Stream.of(user.getRoleIds().split(","))
            .map(Long::valueOf)
            .collect(Collectors.toList()).toArray(new Long[0]);
    }

}
