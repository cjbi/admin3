package tech.wetech.admin3.sys.service;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.wetech.admin3.common.BusinessException;
import tech.wetech.admin3.common.CommonResultStatus;
import tech.wetech.admin3.common.DomainEventPublisher;
import tech.wetech.admin3.common.authz.PermissionHelper;
import tech.wetech.admin3.sys.event.ResourceCreated;
import tech.wetech.admin3.sys.event.ResourceDeleted;
import tech.wetech.admin3.sys.event.ResourceUpdated;
import tech.wetech.admin3.sys.model.Resource;
import tech.wetech.admin3.sys.model.Resource.Type;
import tech.wetech.admin3.sys.repository.ResourceRepository;
import tech.wetech.admin3.sys.service.dto.MenuResourceDTO;
import tech.wetech.admin3.sys.service.dto.ResourceTreeDTO;

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

  public Set<Resource> findResourceByIds(Set<Long> resourceIds) {
    return resourceRepository.findByIds(resourceIds);
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

  @Transactional
  public Resource createResource(String name, Type type, String url, String icon, String permission, Long parentId) {
    Resource resource = new Resource();
    resource.setName(name);
    resource.setType(type);
    resource.setUrl(url);
    resource.setIcon(icon);
    resource.setPermission(permission);
    resource.setParent(findResourceById(parentId));
    resource = resourceRepository.save(resource);
    DomainEventPublisher.instance().publish(new ResourceCreated(resource));
    return resource;
  }

  @Transactional
  public Resource updateResource(Long resourceId, String name, Type type, String url, String icon, String permission, Long parentId) {
    Resource resource = findResourceById(resourceId);
    resource.setName(name);
    resource.setType(type);
    resource.setUrl(url);
    resource.setIcon(icon);
    resource.setPermission(permission);
    resource.setParent(findResourceById(parentId));
    resource = resourceRepository.save(resource);
    DomainEventPublisher.instance().publish(new ResourceUpdated(resource));
    return resource;
  }

  @Transactional
  public void deleteResourceById(Long resourceId) {
    Resource resource = findResourceById(resourceId);
    resourceRepository.delete(resource);
    DomainEventPublisher.instance().publish(new ResourceDeleted(resource));
  }


}
