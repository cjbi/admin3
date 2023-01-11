package tech.wetech.admin3.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping
    public ResponseEntity<PageDTO<User>> findUsers(Pageable pageable, User user) {
        return ResponseEntity.ok(userService.findUsers(pageable, user));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid CreateUserRequest request) {
        Organization organization = organizationService.findOrganization(request.organizationId());
        return ResponseEntity.ok(userService.createUser(request.username(), request.fullName(), request.avatar(), request.gender(), User.State.NORMAL, organization));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody @Valid UpdateUserRequest request) {
        Organization organization = organizationService.findOrganization(request.organizationId());
        return ResponseEntity.ok(userService.updateUser(userId, request.fullName(), request.avatar(), request.gender(), User.State.NORMAL, organization));
    }

    @PostMapping("/{userId}:lock")
    public ResponseEntity<Void> lockUser(@PathVariable Long userId) {
        userService.lockUser(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}:unlock")
    public ResponseEntity<Void> unlockUser(@PathVariable Long userId) {
        userService.unlockUser(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
        return ResponseEntity.ok().build();
    }

    record CreateUserRequest(@NotBlank String username, @NotBlank String fullName, @NotNull User.Gender gender,
                                    @NotBlank String avatar, Long organizationId) {
    }

    record UpdateUserRequest(@NotBlank String fullName, @NotNull User.Gender gender,
                                    @NotBlank String avatar, Long organizationId) {
    }

}
