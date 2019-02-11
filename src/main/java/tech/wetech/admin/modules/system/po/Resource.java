package tech.wetech.admin.modules.system.po;

import org.apache.ibatis.type.JdbcType;
import tech.wetech.admin.modules.system.enums.ResourceType;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author cjbi
 */
@Table(name = "sys_resource")
public class Resource {

    /**
     * 编号
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "资源名称不能为空")
    /**
     * 资源名称
     */
    private String name;
    /**
     * 资源类型
     */
    @NotNull(message = "资源类型不能为空")
    @ColumnType(jdbcType = JdbcType.VARCHAR)
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
    @NotNull(message = "父编号不能为空")
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

    public String makeSelfAsParentIds() {
        return getParentIds() + getId() + "/";
    }

    public boolean isRootNode() {
        return parentId == 0;
    }

    public Long getId() {
        return id;
    }

    public Resource setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Resource setName(String name) {
        this.name = name;
        return this;
    }

    public ResourceType getType() {
        return type;
    }

    public Resource setType(ResourceType type) {
        this.type = type;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Resource setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getPermission() {
        return permission;
    }

    public Resource setPermission(String permission) {
        this.permission = permission;
        return this;
    }

    public Long getParentId() {
        return parentId;
    }

    public Resource setParentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getParentIds() {
        return parentIds;
    }

    public Resource setParentIds(String parentIds) {
        this.parentIds = parentIds;
        return this;
    }

    public Boolean getAvailable() {
        return available;
    }

    public Resource setAvailable(Boolean available) {
        this.available = available;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public Resource setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public Long getPriority() {
        return priority;
    }

    public Resource setPriority(Long priority) {
        this.priority = priority;
        return this;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public Resource setLeaf(Boolean leaf) {
        this.leaf = leaf;
        return this;
    }
}
