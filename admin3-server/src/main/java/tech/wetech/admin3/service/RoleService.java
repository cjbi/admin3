package tech.wetech.admin3.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tech.wetech.admin3.exception.BusinessException;
import tech.wetech.admin3.exception.CommonResultStatus;
import tech.wetech.admin3.model.Resource;
import tech.wetech.admin3.model.Role;
import tech.wetech.admin3.model.User;
import tech.wetech.admin3.repository.ResourceRepository;
import tech.wetech.admin3.repository.RoleRepository;
import tech.wetech.admin3.service.dto.PageDTO;
import tech.wetech.admin3.service.dto.RoleDTO;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author cjbi
 */
@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final ResourceRepository resourceRepository;

    public RoleService(RoleRepository roleRepository, ResourceRepository resourceRepository) {
        this.roleRepository = roleRepository;
        this.resourceRepository = resourceRepository;
    }

    public List<RoleDTO> findRoles() {
        return roleRepository.findAll().stream().map(role -> new RoleDTO(
                        role.getId(),
                        role.getName(),
                        role.getDescription(),
                        role.getAvailable(),
                        role.getResources().stream().map(Resource::getId).toList())
                )
                .toList();
    }

    public Role findRoleById(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new BusinessException(CommonResultStatus.RECORD_NOT_EXIST));
    }

    @Transactional
    public Role createRole(String name, String description, Set<Long> resourceIds) {
        Role role = new Role();
        role.setName(name);
        role.setDescription(description);
        if (!CollectionUtils.isEmpty(resourceIds)) {
            Set<Resource> resources = resourceRepository.findByIds(resourceIds);
            role.setResources(resources);
        }
        return roleRepository.save(role);
    }

    @Transactional
    public Role updateRole(Long roleId, String name, String description, Set<Long> resourceIds) {
        Role role = findRoleById(roleId);
        role.setName(name);
        role.setDescription(description);
        if (!CollectionUtils.isEmpty(resourceIds)) {
            Set<Resource> resources = resourceRepository.findByIds(resourceIds);
            role.setResources(resources);
        }
        return roleRepository.save(role);
    }

    @Transactional
    public void deleteRoleById(Long roleId) {
        roleRepository.deleteById(roleId);
    }

    public PageDTO<User> findRoleUsers(Long roleId, Pageable pageable) {
        Page<User> page = roleRepository.findRoleUsers(roleId, pageable);
        return new PageDTO<>(page.getContent(), page.getTotalElements());
    }
}
