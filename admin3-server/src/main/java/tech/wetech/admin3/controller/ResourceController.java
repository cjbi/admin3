package tech.wetech.admin3.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.wetech.admin3.common.Constants;
import tech.wetech.admin3.common.SessionItemHolder;
import tech.wetech.admin3.service.ResourceService;
import tech.wetech.admin3.service.dto.MenuResourceDTO;
import tech.wetech.admin3.service.dto.ResourceTreeDTO;
import tech.wetech.admin3.service.dto.UserInfoDTO;

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
        UserInfoDTO userInfo = (UserInfoDTO) SessionItemHolder.getItem(Constants.SESSION_CURRENT_USER);
        return ResponseEntity.ok(resourceService.findMenus(userInfo.permissions()));
    }

    @GetMapping("/tree")
    public ResponseEntity<List<ResourceTreeDTO>> findResourceTree() {
        return ResponseEntity.ok(resourceService.findResourceTree());
    }


}
