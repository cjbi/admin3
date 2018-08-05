package tech.wetech.admin.modules.system.service;

import tech.wetech.admin.modules.system.po.Organization;
import tech.wetech.admin.modules.system.dto.TreeDto;
import tk.mybatis.mapper.weekend.Weekend;

import java.util.List;

/**
 * @author cjbi
 */
public interface OrganizationService {

    void createOrganization(Organization organization);

    void updateOrganization(Organization organization);

    void deleteOrganization(Long organizationId);

    Organization findOne(Long organizationId);

    List<Organization> find(Weekend example);

    List<TreeDto> findOrgTree(Long pId);

    List<Organization> findAll();

    List<Organization> findAllWithExclude(Organization excludeOraganization);

    void move(Organization source, Organization target);
}
