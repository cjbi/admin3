package tech.wetech.admin.service.system;

import tech.wetech.admin.common.base.PageResultSet;
import tech.wetech.admin.model.system.entity.Group;

public interface GroupService {

    /**
     * 分页查询用户组
     * @param group
     * @return
     */
    PageResultSet<Group> findByPage(Group group);

    /**
     * 创建用户组
     * @param group
     */
    void createGroup(Group group);

    /**
     * 更新用户组
     * @param group
     */
    void updateGroup(Group group);

    /**
     * 删除用户组
     * @param groupId
     */
    void deleteGroup(Long groupId);

}
