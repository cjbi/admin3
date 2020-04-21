package tech.wetech.admin.model.vo;

import lombok.Data;
import tech.wetech.admin.model.SystemContextHolder;
import tech.wetech.admin.model.dto.PermissionTreeDTO;
import tech.wetech.admin.model.entity.Permission;
import tech.wetech.admin.model.enumeration.PermissionType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cjbi
 */
@Data
public class PermissionTreeVO {

    /**
     * 编号
     */
    private Long id;

    /**
     * 资源名称
     */
    private String name;
    /**
     * 资源类型
     */
    private Integer type;
    /**
     * 资源名称
     */
    private String typeName;
    /**
     * 权限字符串
     */
    private String permission;
    /**
     * 父名称
     */
    private String parentName;
    /**
     * 父编号
     */
    private Long parentId;
    /**
     * 父编号列表
     */
    private String parentIds;
    /**
     * 图标
     */
    private String icon;
    /**
     * 配置
     */
    private String config;

    private Integer status;
    /**
     * 排序
     */
    private Long sort;

    private List<PermissionTreeVO> children;

    public PermissionTreeVO(PermissionTreeDTO permission) {
        this.id = permission.getId();
        this.name = permission.getName();
        this.type = permission.getType();
        this.typeName = PermissionType.fromCode(permission.getType()).getName();
        this.permission = permission.getPermission();
        this.parentId = permission.getParentId();
        //设置上级节点名称
        List<Permission> permissions = SystemContextHolder.getThreadCache("permissions");
        this.parentName = permissions.stream()
                .filter(p -> p.getId().equals(permission.getParentId()))
                .map(Permission::getName).findFirst()
                .orElse("根节点");
        this.parentIds = permission.getParentIds();
        this.icon = permission.getIcon();
        this.config = permission.getConfig();
        this.status = permission.getStatus();
        this.sort = permission.getSort();
        this.children = permission.getChildren().stream()
                .map(PermissionTreeVO::new)
                .collect(Collectors.toList());
        if (this.children.isEmpty()) {
            this.children = null;
        }
    }

}
