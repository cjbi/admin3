package tech.wetech.admin3.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.wetech.admin3.common.authz.RequiresPermissions;
import tech.wetech.admin3.sys.model.Resource;
import tech.wetech.admin3.sys.model.Role;
import tech.wetech.admin3.sys.model.User;
import tech.wetech.admin3.sys.service.ResourceService;
import tech.wetech.admin3.sys.service.RoleService;
import tech.wetech.admin3.sys.service.UserService;
import tech.wetech.admin3.sys.service.dto.PageDTO;
import tech.wetech.admin3.sys.service.dto.RoleDTO;
import tech.wetech.admin3.sys.service.dto.RoleUserDTO;

import java.util.List;
import java.util.Set;

/**
 * @author cjbi
 */
@RestController
@RequestMapping("/roles")
public class RoleController {

  private final UserService userService;
  private final RoleService roleService;
  private final ResourceService resourceService;

  public RoleController(UserService userService, RoleService roleService, ResourceService resourceService) {
    this.userService = userService;
    this.roleService = roleService;
    this.resourceService = resourceService;
  }

  @RequiresPermissions("role:view")
  @GetMapping
  public ResponseEntity<List<RoleDTO>> findRoles() {
    return ResponseEntity.ok(roleService.findRoles());
  }

  @RequiresPermissions("role:view")
  @GetMapping("/{roleId}/users")
  public ResponseEntity<PageDTO<RoleUserDTO>> findRoleUsers(@PathVariable Long roleId, Pageable pageable) {
    return ResponseEntity.ok(roleService.findRoleUsers(roleId, pageable));
  }

  @RequiresPermissions("role:create")
  @PostMapping
  public ResponseEntity<Role> createRole(@RequestBody @Valid RoleRequest request) {
    return new ResponseEntity<>(roleService.createRole(request.name(), request.description()), HttpStatus.CREATED);
  }

  @RequiresPermissions("role:update")
  @PutMapping("/{roleId}/resources")
  public ResponseEntity<Role> changeResources(@PathVariable Long roleId, @RequestBody @Valid RoleResourceRequest request) {
    Set<Resource> resources = resourceService.findResourceByIds(request.resourceIds());
    return ResponseEntity.ok(roleService.changeResources(roleId, resources));
  }

  @RequiresPermissions("role:update")
  @PutMapping("/{roleId}/users")
  public ResponseEntity<Role> changeUsers(@PathVariable Long roleId, @RequestBody @Valid RoleUserRequest request) {
    Set<User> users = userService.findUserByIds(request.userIds());
    return ResponseEntity.ok(roleService.changeUsers(roleId, users));
  }

  @RequiresPermissions("role:update")
  @PutMapping("/{roleId}")
  public ResponseEntity<Role> updateRole(@PathVariable Long roleId, @RequestBody @Valid RoleRequest request) {
    return ResponseEntity.ok(roleService.updateRole(roleId, request.name(), request.description()));
  }

  @RequiresPermissions("role:delete")
  @DeleteMapping("/{roleId}")
  public ResponseEntity<Void> deleteRole(@PathVariable Long roleId) {
    roleService.deleteRoleById(roleId);
    return ResponseEntity.noContent().build();
  }

  record RoleUserRequest(Set<Long> userIds) {
  }

  record RoleResourceRequest(Set<Long> resourceIds) {
  }

  record RoleRequest(@NotBlank String name, String description) {
  }

}
