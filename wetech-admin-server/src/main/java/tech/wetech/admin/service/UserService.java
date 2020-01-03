package tech.wetech.admin.service;

import tech.wetech.admin.exception.BizException;
import tech.wetech.admin.model.entity.User;

import java.util.Set;

public interface UserService extends IService<User> {

    /**
     * 创建用户
     * @param user
     */
    void createUser(User user) throws BizException;

    /**
     * 修改密码
     * @param userId
     * @param newPassword
     */
    void changePassword(Long userId, String newPassword);

    /**
     * 根据用户名查找其角色
     *
     * @param username
     * @return
     */
    Set<String> findRoles(String username);

    /**
     * 根据用户名查找其权限
     *
     * @param username
     * @return
     */
    Set<String> findPermissions(String username);

}