package tech.wetech.admin3.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.wetech.admin3.sys.model.Role;
import tech.wetech.admin3.sys.model.User;
import tech.wetech.admin3.sys.service.RoleService;
import tech.wetech.admin3.sys.service.dto.PageDTO;
import tech.wetech.admin3.sys.service.dto.RoleDTO;

import java.util.List;
import java.util.Set;

/**
 * @author cjbi
 */
@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> findRoles() {
        return ResponseEntity.ok(roleService.findRoles());
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<Role> findRole(@PathVariable Long roleId) {
        return ResponseEntity.ok(roleService.findRoleById(roleId));
    }

    @GetMapping("/{roleId}/users")
    public ResponseEntity<PageDTO<User>> findRoleUsers(@PathVariable Long roleId, Pageable pageable) {
        return ResponseEntity.ok(roleService.findRoleUsers(roleId, pageable));
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody @Valid RoleRequest request) {
        return ResponseEntity.ok(roleService.createRole(request.name(), request.description(), request.resourceIds()));
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<Role> updateRole(@PathVariable Long roleId, @RequestBody @Valid RoleRequest request) {
        return ResponseEntity.ok(roleService.updateRole(roleId, request.name(), request.description(), request.resourceIds()));
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long roleId) {
        roleService.deleteRoleById(roleId);
        return ResponseEntity.ok().build();
    }

    public record RoleRequest(@NotBlank String name, String description, Set<Long> resourceIds) {

    }

}
