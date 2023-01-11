package tech.wetech.admin3.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.wetech.admin3.sys.model.User;
import tech.wetech.admin3.sys.service.UserService;
import tech.wetech.admin3.sys.service.dto.PageDTO;

/**
 * @author cjbi
 */
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<PageDTO<User>> findUsers(Pageable pageable, User user) {
        return ResponseEntity.ok(userService.findUsers(pageable, user));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid CreateUserRequest request) {
        return ResponseEntity.ok(userService.createUser(request.username(), request.fullName(), request.avatar(), request.gender(), User.State.NORMAL));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody @Valid UpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateUser(userId, request.fullName(), request.avatar(), request.gender(), User.State.NORMAL));
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

    public record CreateUserRequest(@NotBlank String username, @NotBlank String fullName, @NotNull User.Gender gender,
                                    @NotBlank String avatar) {
    }

    public record UpdateUserRequest(@NotBlank String fullName, @NotNull User.Gender gender,
                                    @NotBlank String avatar) {
    }

}
