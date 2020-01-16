package tech.wetech.admin.model.vo;

import lombok.Data;
import tech.wetech.admin.model.dto.PermissionDTO;

import java.util.Collections;
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

    private List<PermissionVO> children;

    private List<Object> actionsOptions;
    private Boolean checkedAll;
    private Boolean checked;
    private List<Long> selected;
    private Boolean indeterminate;

    public PermissionVO(PermissionDTO permission) {
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

        this.children = permission.getChildren().stream()
            .map(PermissionVO::new)
            .collect(Collectors.toList());

        this.actionsOptions = permission.getChildren().stream()
            .filter(p -> p.getType() == 2)
            .map(this::getOption)
            .collect(Collectors.toList());
        this.checkedAll = false;
        this.checked = false;
        this.selected = Collections.EMPTY_LIST;
        this.indeterminate = false;
    }

    private Map<String, Object> getOption(PermissionDTO permission) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("label", permission.getName());
        map.put("value", permission.getId());
        return map;
    }


}
