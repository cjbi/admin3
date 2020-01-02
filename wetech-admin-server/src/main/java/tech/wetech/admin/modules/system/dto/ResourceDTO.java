package tech.wetech.admin.modules.system.dto;

import lombok.Data;
import tech.wetech.admin.modules.system.enums.ResourceType;
import tech.wetech.admin.modules.system.po.Resource;

import java.util.List;

/**
 * @author cjbi
 */
@Data
public class ResourceDTO {

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
    private ResourceType type;
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
    private Boolean available;
    /**
     * 图标
     */
    private String icon;
    /**
     * 排序
     */
    private Long priority;

    /**
     * 叶子节点
     */
    private Boolean leaf;

    private List<ResourceDTO> children;

    public ResourceDTO(Resource resource) {
        this.id = resource.getId();
        this.name = resource.getName();
        this.type = resource.getType();
        this.url = resource.getUrl();
        this.permission = resource.getPermission();
        this.parentId = resource.getParentId();
        this.parentIds = resource.getParentIds();
        this.available = resource.getAvailable();
        this.icon = resource.getIcon();
        this.priority = resource.getPriority();
        this.leaf = resource.getLeaf();
    }

}
