package tech.wetech.admin3.sys.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import tech.wetech.admin3.common.CommonResultStatus;
import tech.wetech.admin3.sys.exception.UserException;
import tech.wetech.admin3.sys.model.Organization;
import tech.wetech.admin3.sys.model.User;
import tech.wetech.admin3.sys.repository.OrganizationRepository;
import tech.wetech.admin3.sys.repository.UserRepository;
import tech.wetech.admin3.sys.service.dto.OrgTreeDTO;
import tech.wetech.admin3.sys.service.dto.PageDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cjbi
 */
@Repository
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;

    public OrganizationService(OrganizationRepository organizationRepository, UserRepository userRepository) {
        this.organizationRepository = organizationRepository;
        this.userRepository = userRepository;
    }

    public PageDTO<User> findOrgUsers(Pageable pageable, Long organizationId) {
        Organization organization = findOrganization(organizationId);
        Page<User> page = userRepository.findOrgUsers(pageable, organization, organization.makeSelfAsParentIds());
        return new PageDTO<>(page.getContent(), page.getTotalElements());
    }

    public boolean existsUsers(Organization organization) {
        String orgParentIds = organization.makeSelfAsParentIds();
        return userRepository.countOrgUsers(orgParentIds) > 0;
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
        if (existsUsers(organization)) {
            throw new UserException(CommonResultStatus.FAIL, "节点存在用户，不能删除");
        }
        organizationRepository.delete(organization);
    }

    public List<OrgTreeDTO> findOrgTree(Long parentId) {
        return organizationRepository.findByParentId(parentId).stream()
                .map(OrgTreeDTO::new)
                .collect(Collectors.toList());
    }
}
