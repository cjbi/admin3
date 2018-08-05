package tech.wetech.admin.modules.system.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tech.wetech.admin.core.utils.PageResultSet;
import tech.wetech.admin.modules.system.mapper.GroupMapper;
import tech.wetech.admin.modules.system.po.Group;
import tech.wetech.admin.modules.system.query.GroupQuery;
import tech.wetech.admin.modules.system.service.GroupService;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public PageResultSet<Group> findByPage(GroupQuery groupQuery) {

        if (!StringUtils.isEmpty(groupQuery.getOrderBy())) {
            PageHelper.orderBy(groupQuery.getOrderBy());
        }
        Weekend<Group> weekend = Weekend.of(Group.class);
        WeekendCriteria<Group, Object> criteria = weekend.weekendCriteria();
        if (!StringUtils.isEmpty(groupQuery.getName())) {
            criteria.andLike(Group::getName, groupQuery.getName());
        }

        PageResultSet<Group> resultSet = new PageResultSet<>();
        Page page = PageHelper.offsetPage(groupQuery.getOffset(), groupQuery.getLimit()).doSelectPage(() -> groupMapper.selectByExample(weekend));
        resultSet.setRows(page);
        resultSet.setTotal(page.getTotal());
        return resultSet;
    }

    @Override
    public List<Group> findAll() {
        return groupMapper.selectAll();
    }

    @Override
    public Group findOne(Long groupId) {
        return groupMapper.selectByPrimaryKey(groupId);
    }

    @Override
    public void createGroup(Group group) {
        groupMapper.insertSelective(group);
    }

    @Override
    public void updateGroup(Group group) {
        groupMapper.updateByPrimaryKeySelective(group);
    }

    @Override
    public void deleteGroup(Long groupId) {
        groupMapper.deleteByPrimaryKey(groupId);
    }
}
