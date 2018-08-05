package tech.wetech.admin.modules.system.service;

import tech.wetech.admin.core.utils.PageResultSet;
import tech.wetech.admin.core.exception.BizException;
import tech.wetech.admin.modules.system.po.User;
import tech.wetech.admin.modules.system.dto.UserDto;
import tech.wetech.admin.modules.system.query.UserQuery;

import java.util.List;
import java.util.Set;

public interface UserService {

    PageResultSet<UserDto> findByPage(UserQuery userQuery);

    /**
     * 创建用户
     * @param user
     */
    void createUser(User user) throws BizException;

    void updateUser(User user);

    void deleteUser(Long userId);

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    void changePassword(Long userId, String newPassword);


    User findOne(Long userId);

    List<User> findAll();

    /**
     * 根据用户名查找用户
     * @param username
     * @return
     */
    public User findByUsername(String username);

    /**
     * 根据用户名查找其角色
     * @param username
     * @return
     */
    Set<String> findRoles(String username);

    /**
     * 根据用户名查找其权限
     * @param username
     * @return
     */
    Set<String> findPermissions(String username);

}