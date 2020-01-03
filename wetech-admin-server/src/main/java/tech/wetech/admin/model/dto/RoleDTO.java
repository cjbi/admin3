package tech.wetech.admin.model.dto;

import lombok.Data;
import tech.wetech.admin.model.entity.Role;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class RoleDTO {

    private Long id; // 编号
    private String role; // 角色标识 程序中判断使用,如"admin"
    private String description; // 角色描述,UI界面显示使用
    private String resourceIds; // 拥有的资源
    private List<Long> resourceIdList;
    private Boolean available; // 是否可用,如果不可用将不会添加给用户
    private String resourceNames;

    public RoleDTO() {

    }

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.role = role.getRole();
        this.description = role.getDescription();
        this.resourceIds = role.getResourceIds();
        this.resourceIdList = Arrays.asList(role.getResourceIds().split(",")).stream().map(Long::valueOf).collect(Collectors.toList());
        this.available = role.getAvailable();
    }
}
