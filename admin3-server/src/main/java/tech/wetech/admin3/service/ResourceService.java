package tech.wetech.admin3.service;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import tech.wetech.admin3.exception.BusinessException;
import tech.wetech.admin3.exception.CommonResultStatus;
import tech.wetech.admin3.model.PermissionHelper;
import tech.wetech.admin3.model.Resource;
import tech.wetech.admin3.model.Resource.Type;
import tech.wetech.admin3.repository.ResourceRepository;
import tech.wetech.admin3.service.dto.MenuResourceDTO;
import tech.wetech.admin3.service.dto.ResourceTreeDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static tech.wetech.admin3.common.Constants.RESOURCE_ROOT_ID;

/**
 * @author cjbi
 */
@Service
public class ResourceService {

    private final ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public Resource findResourceById(Long resourceId) {
        return resourceRepository.findById(resourceId)
                .orElseThrow(() -> new BusinessException(CommonResultStatus.RECORD_NOT_EXIST));
    }

    public List<MenuResourceDTO> findMenus(Set<String> permissions) {
        Resource probe = new Resource();
        probe.setType(Type.MENU);
        List<Resource> allMenus = resourceRepository.findAll(Example.of(probe));
        List<MenuResourceDTO> list = new ArrayList<>();
        for (Resource menu : allMenus) {
            if (!PermissionHelper.hasPermission(permissions, menu.getPermission())) {
                continue;
            }
            list.add(new MenuResourceDTO(menu.getId(), menu.getName(), menu.getUrl(), menu.getIcon(), menu.getParent().getId()));
        }
        return list;
    }


    public List<ResourceTreeDTO> findResourceTree() {
        List<Resource> allResources = resourceRepository.findAll();
        return getResourceTree(allResources, RESOURCE_ROOT_ID);
    }

    private List<ResourceTreeDTO> getResourceTree(List<Resource> resources, Long parentId) {
        return resources.stream()
                .filter(r -> r.getParent() != null && r.getParent().getId().equals(parentId))
                .map(r -> new ResourceTreeDTO(r.getId(), r.getName(), r.getType(), r.getPermission(), r.getUrl(), r.getIcon(), getResourceTree(resources, r.getId()), r.getParent().getId(), r.getParent().getName()))
                .collect(Collectors.toList());
    }

    public Resource createResource(String name, Type type, String url, String icon, String permission, Long parentId) {
        Resource resource = new Resource();
        resource.setName(name);
        resource.setType(type);
        resource.setUrl(url);
        resource.setIcon(icon);
        resource.setPermission(permission);
        resource.setParent(findResourceById(parentId));
        resource.setAvailable(true);
        return resourceRepository.save(resource);
    }

    public Resource updateResource(Long resourceId, String name, Type type, String url, String icon, String permission, Long parentId) {
        Resource resource = findResourceById(resourceId);
        resource.setName(name);
        resource.setType(type);
        resource.setUrl(url);
        resource.setIcon(icon);
        resource.setPermission(permission);
        resource.setParent(findResourceById(parentId));
        return resourceRepository.save(resource);
    }

    public void deleteResourceById(Long resourceId) {
        resourceRepository.deleteById(resourceId);
    }


}
