package tech.wetech.admin.model.vo;

import lombok.Data;
import tech.wetech.admin.model.dto.PermissionDTO;
import tech.wetech.admin.model.dto.RoleDTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author cjbi
 */
@Data
public class RoleVO {
    /**
     * 编号
     */
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
    private List<PermissionVO> permissions;

    public RoleVO() {
    }

    public RoleVO(RoleDTO role, List<PermissionDTO> permissions) {
        this.id = role.getId();
        this.role = role.getRole();
        this.name = role.getName();
        this.description = role.getDescription();
        this.status = role.getStatus();
    }

    private List<Long> getPermissionIds(String permissionIdStr) {
        return Stream.of(permissionIdStr.split(","))
            .map(Long::valueOf)
            .collect(Collectors.toList());
    }
}
