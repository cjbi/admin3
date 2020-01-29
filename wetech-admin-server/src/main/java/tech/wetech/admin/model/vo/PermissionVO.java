package tech.wetech.admin.model.vo;

import lombok.Data;
import tech.wetech.admin.model.dto.PermissionDTO;
import tech.wetech.admin.model.enumeration.PermissionType;
import tech.wetech.admin.utils.JSONUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author cjbi
 */
@Data
public class PermissionVO {

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
    private Map<String, Object> config;

    private Integer status;
    /**
     * 排序
     */
    private Long sort;

    private List<PermissionVO> children;

    public PermissionVO(PermissionDTO permission) {
        this.id = permission.getId();
        this.name = permission.getName();
        this.type = permission.getType();
        this.typeName = PermissionType.fromCode(permission.getType()).getName();
        this.permission = permission.getPermission();
        this.parentId = permission.getParentId();
        this.parentIds = permission.getParentIds();
        this.icon = permission.getIcon();
        if (permission.getConfig() != null) {
            this.config = JSONUtil.toObject(permission.getConfig(), Map.class);
        }
        this.status = permission.getStatus();
        this.sort = permission.getSort();
        if (permission.getChildren() != null) {
            this.children = permission.getChildren().stream()
                .map(PermissionVO::new)
                .collect(Collectors.toList());
        }
    }

    private Map<String, Object> getOption(PermissionDTO permission) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("label", permission.getName());
        map.put("value", permission.getId());
        return map;
    }


}
