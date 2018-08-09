package tech.wetech.admin.modules.system.po;

import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cjbi
 */
@Table(name = "sys_role")
public class Role {

    /**
     * 编号
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 角色标识 程序中判断使用,如"admin"
     */
    @NotBlank(message = "角色标识不能为空")
    private String role;
    /**
     * 角色描述,UI界面显示使用
     */
    @NotBlank(message = "角色描述不能为空")
    private String description;
    /**
     * 拥有的资源
     */
    @NotBlank(message = "拥有的资源不能为空")
    private String resourceIds;
    /**
     * 拥有的资料列表
     */
    @Transient
    private List<Long> resourceIdList;
    /**
     * 是否可用,如果不可用将不会添加给用户
     */
    private Boolean available = Boolean.FALSE;

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

}
