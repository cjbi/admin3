package tech.wetech.admin.model.system;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.StringTypeHandler;
import tech.wetech.admin.model.BaseEntity;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author cjbi
 */
@Table(name = "sys_resource")
public class Resource extends BaseEntity {

    @NotNull(message = "资源名称不能为空")
    /**
     * 资源名称
     */
    private String name;
    /**
     * 资源类型
     */
    @NotNull(message = "资源类型不能为空")
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private ResourceType type = ResourceType.menu;
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
    private Boolean available = Boolean.FALSE;
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
    private Boolean leaf = Boolean.FALSE;

    @Transient
    private List<Resource> children;

    public List<Resource> getChildren() {
        return children;
    }

    public void setChildren(List<Resource> children) {
        this.children = children;
    }

    public static enum ResourceType {
        menu("菜单"), button("按钮");

        private final String info;

        private ResourceType(String info) {
            this.info = info;
        }

        public String getInfo() {
            return info;
        }
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
}
