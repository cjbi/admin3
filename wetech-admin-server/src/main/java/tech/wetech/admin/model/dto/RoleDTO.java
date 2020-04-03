package tech.wetech.admin.model.dto;

import lombok.Data;
import org.springframework.util.ObjectUtils;
import tech.wetech.admin.model.entity.Role;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class RoleDTO {

    private Long id;
    /**
     * 角色标识 程序中判断使用,如"admin"
     */
    private String role;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色描述,UI界面显示使用
     */
    private String description;
    /**
     * 状态,如果不可用将不会添加给用户。1.正常 0.禁用
     */
    private Integer status;
    /**
     * 拥有的权限
     */
    private List<Long> permissionIds;

    public RoleDTO() {
    }

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.role = role.getRole();
        this.name = role.getName();
        this.description = role.getDescription();
        this.status = role.getStatus();
        this.permissionIds = getPermissionIds(role.getPermissionIds());
    }

    private List<Long> getPermissionIds(String permissionIdStr) {
        if (ObjectUtils.isEmpty(permissionIdStr)) {
            return Collections.emptyList();
        }
        return Stream.of(permissionIdStr.split(","))
            .map(Long::valueOf)
            .collect(Collectors.toList());
    }

}
