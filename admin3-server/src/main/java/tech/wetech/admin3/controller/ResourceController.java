package tech.wetech.admin3.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.wetech.admin3.common.Constants;
import tech.wetech.admin3.common.SessionItemHolder;
import tech.wetech.admin3.sys.model.Resource;
import tech.wetech.admin3.sys.service.ResourceService;
import tech.wetech.admin3.sys.service.dto.MenuResourceDTO;
import tech.wetech.admin3.sys.service.dto.ResourceTreeDTO;
import tech.wetech.admin3.sys.service.dto.UserinfoDTO;

import java.util.List;

/**
 * @author cjbi
 */
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/resources")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping("/menu")
    public ResponseEntity<List<MenuResourceDTO>> findMenus() {
        UserinfoDTO userInfo = (UserinfoDTO) SessionItemHolder.getItem(Constants.SESSION_CURRENT_USER);
        return ResponseEntity.ok(resourceService.findMenus(userInfo.permissions()));
    }

    @GetMapping("/tree")
    public ResponseEntity<List<ResourceTreeDTO>> findResourceTree() {
        return ResponseEntity.ok(resourceService.findResourceTree());
    }

    @PostMapping
    public ResponseEntity<Resource> createResource(@RequestBody ResourceRequest request) {
        return ResponseEntity.ok(resourceService.createResource(request.name(), request.type(), request.url(), request.icon(), request.permission(), request.parentId()));
    }

    @PutMapping("/{resourceId}")
    public ResponseEntity<Resource> updateResource(@PathVariable Long resourceId, @RequestBody ResourceRequest request) {
        return ResponseEntity.ok(resourceService.updateResource(resourceId, request.name(), request.type(), request.url(), request.icon(), request.permission(), request.parentId()));
    }

    @DeleteMapping("/{resourceId}")
    public ResponseEntity<Void> deleteResource(@PathVariable Long resourceId) {
        resourceService.deleteResourceById(resourceId);
        return ResponseEntity.ok().build();
    }


    public record ResourceRequest(String name,
                                  Resource.Type type,
                                  String url,
                                  String icon,
                                  String permission,
                                  Long parentId) {

    }

}
