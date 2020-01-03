package tech.wetech.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.wetech.admin.service.BaseService;
import tech.wetech.admin.mapper.GroupMapper;
import tech.wetech.admin.service.GroupService;
import tech.wetech.admin.model.entity.Group;

@Service
public class GroupServiceImpl extends BaseService<Group> implements GroupService {

    @Autowired
    private GroupMapper groupMapper;
}
