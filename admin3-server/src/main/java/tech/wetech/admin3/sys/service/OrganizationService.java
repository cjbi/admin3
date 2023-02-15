package tech.wetech.admin3.sys.service;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import tech.wetech.admin3.common.CommonResultStatus;
import tech.wetech.admin3.common.DomainEventPublisher;
import tech.wetech.admin3.sys.event.OrganizationCreated;
import tech.wetech.admin3.sys.event.OrganizationDeleted;
import tech.wetech.admin3.sys.event.OrganizationUpdated;
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

  @Transactional
  public Organization createOrganization(String name, Organization.Type type, Long parentId) {
    Organization organization = new Organization();
    organization.setName(name);
    organization.setType(type);
    Organization parent = findOrganization(parentId);
    organization.setParent(findOrganization(parentId));
    organization.setParentIds(parent.makeSelfAsParentIds());
    organization = organizationRepository.save(organization);
    DomainEventPublisher.instance().publish(new OrganizationCreated(organization));
    return organization;
  }

  @Transactional
  public Organization updateOrganization(Long id, String name) {
    Organization organization = findOrganization(id);
    organization.setName(name);
    organization = organizationRepository.save(organization);
    DomainEventPublisher.instance().publish(new OrganizationUpdated(organization));
    return organization;
  }

  @Transactional
  public void deleteOrganization(Long id) {
    Organization organization = findOrganization(id);
    organizationRepository.delete(organization);
    DomainEventPublisher.instance().publish(new OrganizationDeleted(organization));
  }

  public List<OrgTreeDTO> findOrgTree(Long parentId) {
    return organizationRepository.findByParentId(parentId).stream()
      .map(OrgTreeDTO::new)
      .collect(Collectors.toList());
  }
}
