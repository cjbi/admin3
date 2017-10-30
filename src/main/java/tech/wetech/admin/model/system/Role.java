package tech.wetech.admin.model.system;

import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Role implements Serializable{
    private Long id; // 编号
    private String role; // 角色标识 程序中判断使用,如"admin"
    private String description; // 角色描述,UI界面显示使用
    private String resourceIds; // 拥有的资源
    private List<Long> resourceIdList;
    private Boolean available = Boolean.FALSE; // 是否可用,如果不可用将不会添加给用户

    public Role() {
    }

    public Role(String role, String description, Boolean available) {
        this.role = role;
        this.description = description;
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        String[] resourceIdStrs = resourceIds.split(",");
        for (String resourceId : resourceIdStrs) {
            if (StringUtils.isEmpty(resourceId)) {
                continue;
            }
            getResourceIdList().add(Long.valueOf(resourceId));
        }
        this.resourceIds = resourceIds;
    }

    public List<Long> getResourceIdList() {
        if (resourceIdList == null) {
            resourceIdList = new ArrayList<>();
        }
        return resourceIdList;
    }

    public void setResourceIdList(List<Long> resourceIdList) {
        StringBuilder s = new StringBuilder();
        for (Long resourceId : resourceIdList) {
            s.append(resourceId);
            s.append(",");
        }
        if (s.length() > 0) {
            s.deleteCharAt(s.length() - 1);
        }
        this.resourceIds = s.toString();
        this.resourceIdList = resourceIdList;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Role role = (Role) o;

        if (id != null ? !id.equals(role.id) : role.id != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Role{" + "id=" + id + ", role='" + role + '\'' + ", description='" + description + '\''
                + ", resourceIds=" + resourceIds + ", available=" + available + '}';
    }
}
