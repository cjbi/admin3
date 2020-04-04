package tech.wetech.admin.model.dto;

import lombok.Data;
import tech.wetech.admin.model.entity.Permission;

import java.util.List;

/**
 * @author cjbi
 */
@Data
public class PermissionTreeDTO {

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
    /**
     * 图标
     */
    private String icon;
    /**
     * 配置
     */
    private String config;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 排序
     */
    private Long sort;

    private List<PermissionTreeDTO> children;

    public PermissionTreeDTO(Permission permission) {
        this.id = permission.getId();
        this.name = permission.getName();
        this.type = permission.getType();
        this.permission = permission.getPermission();
        this.parentId = permission.getParentId();
        this.parentIds = permission.getParentIds();
        this.icon = permission.getIcon();
        this.config = permission.getConfig();
        this.status = permission.getStatus();
        this.sort = permission.getSort();
    }

}
