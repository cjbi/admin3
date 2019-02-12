package tech.wetech.admin.modules.system.mapper;

import tech.wetech.admin.modules.base.mapper.MyMapper;
import tech.wetech.admin.modules.system.po.Organization;

public interface OrganizationMapper extends MyMapper<Organization> {

    int updateSalefParentIds(String makeSelfAsParentIds);

}