package tech.wetech.admin3.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.wetech.admin3.common.CommonResultStatus;
import tech.wetech.admin3.sys.exception.UserException;
import tech.wetech.admin3.sys.model.Organization;
import tech.wetech.admin3.sys.model.User;
import tech.wetech.admin3.sys.service.OrganizationService;
import tech.wetech.admin3.sys.service.UserService;
import tech.wetech.admin3.sys.service.dto.OrgTreeDTO;
import tech.wetech.admin3.sys.service.dto.PageDTO;

import java.util.List;

import static tech.wetech.admin3.sys.model.Organization.Type;

/**
 * @author cjbi
 */
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    private final UserService userService;
    private final OrganizationService organizationService;

    public OrganizationController(UserService userService, OrganizationService organizationService) {
        this.userService = userService;
        this.organizationService = organizationService;
    }

    @GetMapping("/tree")
    public ResponseEntity<List<OrgTreeDTO>> findOrgTree(Long parentId) {
        return ResponseEntity.ok(organizationService.findOrgTree(parentId));
    }

    @GetMapping("/{organizationId}/users")
    public ResponseEntity<PageDTO<User>> findOrgUsers(Pageable pageable, String username, User.State state, @PathVariable Long organizationId) {
        Organization organization = organizationService.findOrganization(organizationId);
        return ResponseEntity.ok(userService.findOrgUsers(pageable, username, state, organization));
    }

    @PostMapping
    public ResponseEntity<Organization> createOrganization(@RequestBody @Valid OrganizationRequest request) {
        return ResponseEntity.ok(organizationService.createOrganization(request.name(), request.type(), request.parentId()));
    }

    @PutMapping("/{organizationId}")
    public ResponseEntity<Organization> updateOrganization(@PathVariable Long organizationId, @RequestBody @Valid OrganizationRequest request) {
        return ResponseEntity.ok(organizationService.updateOrganization(organizationId, request.name()));
    }

    @DeleteMapping("/{organizationId}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long organizationId) {
        Organization organization = organizationService.findOrganization(organizationId);
        if (userService.existsUsers(organization)) {
            throw new UserException(CommonResultStatus.FAIL, "节点存在用户，不能删除");
        }
        organizationService.deleteOrganization(organizationId);
        return ResponseEntity.ok().build();
    }

    public record OrganizationRequest(@NotBlank String name, @NotNull Type type, @NotNull Long parentId) {

    }


}
