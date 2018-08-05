package tech.wetech.admin.modules.system.service;

import tech.wetech.admin.core.utils.PageResultSet;
import tech.wetech.admin.modules.system.po.Group;
import tech.wetech.admin.modules.system.query.GroupQuery;

import java.util.List;

/**
 * 组服务接口
 * @author cjbi
 */
public interface GroupService {

    /**
     * 分页查询用户组
     * @param groupQuery
     * @return
     */
    PageResultSet<Group> findByPage(GroupQuery groupQuery);

    /**
     * 查询所有
     * @return
     */
    List<Group> findAll();

    /**
     * 查询单个
     * @param groupId
     * @return
     */
    Group findOne(Long groupId);

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
