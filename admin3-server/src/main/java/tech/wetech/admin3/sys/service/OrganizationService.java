package tech.wetech.admin3.sys.service;

import org.springframework.stereotype.Repository;
import tech.wetech.admin3.common.CommonResultStatus;
import tech.wetech.admin3.sys.exception.UserException;
import tech.wetech.admin3.sys.model.Organization;
import tech.wetech.admin3.sys.repository.OrganizationRepository;
import tech.wetech.admin3.sys.repository.UserRepository;
import tech.wetech.admin3.sys.service.dto.OrgTreeDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cjbi
 */
@Repository
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository, UserRepository userRepository) {
        this.organizationRepository = organizationRepository;
    }

    public Organization findOrganization(Long id) {
        return organizationRepository.findById(id).orElseThrow(() -> new UserException(CommonResultStatus.RECORD_NOT_EXIST));
    }

    public Organization createOrganization(String name, Organization.Type type, Long parentId) {
        Organization organization = new Organization();
        organization.setName(name);
        organization.setType(type);
        Organization parent = findOrganization(parentId);
        organization.setParent(findOrganization(parentId));
        organization.setParentIds(parent.makeSelfAsParentIds());
        organization = organizationRepository.save(organization);
        return organization;
    }

    public Organization updateOrganization(Long id, String name) {
        Organization organization = findOrganization(id);
        organization.setName(name);
        return organizationRepository.save(organization);
    }

    public void deleteOrganization(Long id) {
        Organization organization = findOrganization(id);
        organizationRepository.delete(organization);
    }

    public List<OrgTreeDTO> findOrgTree(Long parentId) {
        return organizationRepository.findByParentId(parentId).stream()
                .map(OrgTreeDTO::new)
                .collect(Collectors.toList());
    }
}
