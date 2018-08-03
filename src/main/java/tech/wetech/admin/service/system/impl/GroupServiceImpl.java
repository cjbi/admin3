package tech.wetech.admin.service.system.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tech.wetech.admin.common.base.PageResultSet;
import tech.wetech.admin.mapper.system.GroupMapper;
import tech.wetech.admin.model.system.entity.Group;
import tech.wetech.admin.service.system.GroupService;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public PageResultSet<Group> findByPage(Group group) {
        PageHelper.offsetPage(group.getOffset(), group.getLimit());
        if (!StringUtils.isEmpty(group.getOrderBy())) {
            PageHelper.orderBy(group.getOrderBy());
        }
        Weekend<Group> weekend = Weekend.of(Group.class);
        WeekendCriteria<Group, Object> criteria = weekend.weekendCriteria();
        if (!StringUtils.isEmpty(group.getName())) {
            criteria.andLike(Group::getName, group.getName());
        }
        if (!StringUtils.isEmpty(group.getType())) {
            criteria.andEqualTo(Group::getType, group.getType());
        }
        PageResultSet<Group> resultSet = new PageResultSet<>();
        Page list = (Page) groupMapper.selectByExample(weekend);
        resultSet.setRows(list);
        resultSet.setTotal(list.getTotal());
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
