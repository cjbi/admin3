package tech.wetech.admin.modules.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.wetech.admin.modules.base.service.impl.BaseService;
import tech.wetech.admin.modules.system.mapper.GroupMapper;
import tech.wetech.admin.modules.system.po.Group;
import tech.wetech.admin.modules.system.service.GroupService;

@Service
public class GroupServiceImpl extends BaseService<Group> implements GroupService {

    @Autowired
    private GroupMapper groupMapper;
}
