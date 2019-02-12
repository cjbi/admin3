package tech.wetech.admin.modules.system.dto;

import tech.wetech.admin.modules.system.enums.ResourceType;
import tech.wetech.admin.modules.system.po.Resource;

import java.util.List;

/**
 * @author cjbi
 */
public class ResourceDto {

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

    private List<ResourceDto> children;

    public ResourceDto(Resource resource) {
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

    public List<ResourceDto> getChildren() {
        return children;
    }

    public void setChildren(List<ResourceDto> children) {
        this.children = children;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public boolean isRootNode() {
        return parentId == 0;
    }

    public String makeSelfAsParentIds() {
        return getParentIds() + getId() + "/";
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public String getTypeName() {
        return type.getInfo();
    }

}
