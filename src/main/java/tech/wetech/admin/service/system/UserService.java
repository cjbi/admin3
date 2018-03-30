package tech.wetech.admin.service.system;

import tech.wetech.admin.common.base.Page;
import tech.wetech.admin.common.base.PageResultSet;
import tech.wetech.admin.common.exception.BizException;
import tech.wetech.admin.model.system.User;
import tech.wetech.admin.model.system.UserDto;
import tech.wetech.admin.model.system.UserExample;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> selectByExample(UserExample example);

    PageResultSet<UserDto> findByPage(Page page);

    /**
     * 创建用户
     * @param user
     */
    public int createUser(User user) throws BizException;

    public int updateUser(User user);

    public void deleteUser(Long userId);

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