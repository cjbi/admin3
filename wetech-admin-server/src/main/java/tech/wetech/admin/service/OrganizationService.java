package tech.wetech.admin.service;

import tech.wetech.admin.service.IService;
import tech.wetech.admin.model.dto.TreeDTO;
import tech.wetech.admin.model.entity.Organization;

import java.util.List;

/**
 * @author cjbi
 */
public interface OrganizationService extends IService<Organization> {

    void createOrganization(Organization organization);

    List<TreeDTO> queryOrgTree(Long pId);

    List<Organization> queryAllWithExclude(Organization excludeOraganization);

    void move(Organization source, Organization target);
}
