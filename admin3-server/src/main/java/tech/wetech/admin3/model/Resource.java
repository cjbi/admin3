package tech.wetech.admin3.model;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 资源
 *
 * @author cjbi
 */
@Entity
public class Resource extends EntityBase {

    private String name;

    private Type type;

    private String permission;

    @ManyToOne(fetch = FetchType.LAZY)
    private Resource parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private Set<Resource> children = new LinkedHashSet<>();

    private String parentIds; //父编号列表

    private Boolean available;

    private String url;

    public enum Type {
        MENU, BUTTON
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Resource getParent() {
        return parent;
    }

    public void setParent(Resource parent) {
        this.parent = parent;
    }

    public Set<Resource> getChildren() {
        return children;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
