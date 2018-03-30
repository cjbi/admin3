package tech.wetech.admin.mapper.system;

import org.apache.ibatis.annotations.Param;
import tech.wetech.admin.model.system.Organization;
import tech.wetech.admin.model.system.OrganizationExample;

import java.util.List;

public interface OrganizationMapper {
    long countByExample(OrganizationExample example);

    int deleteByExample(OrganizationExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Organization record);

    int insertSelective(Organization record);

    List<Organization> selectByExample(OrganizationExample example);

    Organization selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Organization record, @Param("example") OrganizationExample example);

    int updateByExample(@Param("record") Organization record, @Param("example") OrganizationExample example);

    int updateByPrimaryKeySelective(Organization record);

    int updateByPrimaryKey(Organization record);

    int updateSalefParentIds(String makeSelfAsParentIds);
}