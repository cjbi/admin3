package tech.wetech.admin3.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.wetech.admin3.common.authz.RequiresPermissions;
import tech.wetech.admin3.sys.model.Organization;
import tech.wetech.admin3.sys.model.User;
import tech.wetech.admin3.sys.service.OrganizationService;
import tech.wetech.admin3.sys.service.UserService;
import tech.wetech.admin3.sys.service.dto.PageDTO;

/**
 * @author cjbi
 */
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/users")
public class UserController {

  private final OrganizationService organizationService;
  private final UserService userService;

  public UserController(OrganizationService organizationService, UserService userService) {
    this.organizationService = organizationService;
    this.userService = userService;
  }

  @RequiresPermissions("user:view")
  @GetMapping
  public ResponseEntity<PageDTO<User>> findUsers(Pageable pageable, User user) {
    return ResponseEntity.ok(userService.findUsers(pageable, user));
  }

  @RequiresPermissions("user:create")
  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody @Valid CreateUserRequest request) {
    Organization organization = organizationService.findOrganization(request.organizationId());
    return new ResponseEntity<>(userService.createUser(request.username(), request.avatar(), request.gender(), User.State.NORMAL, organization), HttpStatus.CREATED);
  }

  @RequiresPermissions("user:update")
  @PutMapping("/{userId}")
  public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody @Valid UpdateUserRequest request) {
    Organization organization = organizationService.findOrganization(request.organizationId());
    return ResponseEntity.ok(userService.updateUser(userId, request.avatar(), request.gender(), User.State.NORMAL, organization));
  }

  @RequiresPermissions("user:update")
  @PostMapping("/{userId}:disable")
  public ResponseEntity<User> disableUser(@PathVariable Long userId) {
    return ResponseEntity.ok(userService.disableUser(userId));
  }

  @RequiresPermissions("user:update")
  @PostMapping("/{userId}:enable")
  public ResponseEntity<User> enableUser(@PathVariable Long userId) {
    return ResponseEntity.ok(userService.enableUser(userId));
  }

  @RequiresPermissions("user:delete")
  @DeleteMapping("/{userId}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
    userService.delete(userId);
    return ResponseEntity.noContent().build();
  }

  record CreateUserRequest(@NotBlank String username, @NotNull User.Gender gender,
                           @NotBlank String avatar, Long organizationId) {
  }

  record UpdateUserRequest(@NotNull User.Gender gender,
                           @NotBlank String avatar, Long organizationId) {
  }

}
