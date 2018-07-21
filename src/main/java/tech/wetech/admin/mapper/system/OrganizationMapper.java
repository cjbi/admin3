package tech.wetech.admin.mapper.system;

import tech.wetech.admin.common.utils.MyMapper;
import tech.wetech.admin.model.system.entity.Organization;

public interface OrganizationMapper extends MyMapper<Organization> {

    int updateSalefParentIds(String makeSelfAsParentIds);

}