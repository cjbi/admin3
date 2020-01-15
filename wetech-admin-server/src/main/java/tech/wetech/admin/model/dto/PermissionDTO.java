package tech.wetech.admin.model.dto;

import lombok.Data;
import tech.wetech.admin.model.entity.Permission;

import java.util.List;

/**
 * @author cjbi
 */
@Data
public class PermissionDTO {

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
     * 资源路径
     */
    private String url;
    /**
     * 权限字符串
     */
    private String permission;
    /**
     * 父编号
     */
    private Long parentId;
    /**
     * 父编号列表
     */
    private String parentIds;
    private Integer status;
    /**
     * 图标
     */
    private String icon;
    /**
     * 排序
     */
    private Long order;

    private List<PermissionDTO> children;

    public PermissionDTO(Permission permission) {
        this.id = permission.getId();
        this.name = permission.getName();
        this.type = permission.getType();
        this.url = permission.getUrl();
        this.permission = permission.getPermission();
        this.parentId = permission.getParentId();
        this.parentIds = permission.getParentIds();
        this.status = permission.getStatus();
        this.icon = permission.getIcon();
        this.order = permission.getOrder();
    }

}
