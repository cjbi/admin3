package tech.wetech.admin.service.system;

import tech.wetech.admin.model.system.Organization;
import tech.wetech.admin.model.system.OrganizationExample;

import java.util.List;

public interface OrganizationService {

    int createOrganization(Organization organization);

    int updateOrganization(Organization organization);

    void deleteOrganization(Long organizationId);

    Organization findOne(Long organizationId);

    public List<Organization> find(OrganizationExample example);

    List<Organization> findAll();

    List<Organization> findAllWithExclude(Organization excludeOraganization);

    void move(Organization source, Organization target);
}
