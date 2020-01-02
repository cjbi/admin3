package tech.wetech.admin.modules.system.mapper;

import org.apache.ibatis.annotations.Update;
import tech.wetech.admin.modules.system.po.Organization;
import tech.wetech.mybatis.mapper.BaseMapper;

public interface OrganizationMapper extends BaseMapper<Organization> {

    @Update("update sys_organization set parent_ids=concat(#{makeSelfAsParentIds}, substring(parent_ids, length(#{makeSelfAsParentIds}))) where parent_ids like #{makeSelfAsParentIds} + '%'")
    int updateSalefParentIds(String makeSelfAsParentIds);

}