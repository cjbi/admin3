package tech.wetech.admin.mapper;

import tech.wetech.admin.model.entity.User;
import tech.wetech.mybatis.mapper.BaseMapper;

public interface UserMapper extends BaseMapper<User> {

    /**
     * 获取单个用户
     * @param username
     * @return
     */
    default User selectByUsername(String username) {
        return this.createCriteria().andEqualTo(User::getUsername, username).selectOne();
    }

}
