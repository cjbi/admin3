package tech.wetech.admin.modules.system.service;

import tech.wetech.admin.modules.base.service.IService;
import tech.wetech.admin.modules.system.dto.TreeDto;
import tech.wetech.admin.modules.system.po.Organization;

import java.util.List;

/**
 * @author cjbi
 */
public interface OrganizationService extends IService<Organization> {

    void createOrganization(Organization organization);

    List<TreeDto> queryOrgTree(Long pId);

    List<Organization> queryAllWithExclude(Organization excludeOraganization);

    void move(Organization source, Organization target);
}
