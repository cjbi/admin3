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
     * 是否可用,如果不可用将不会添加给用户
     */
    private Boolean available;

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

    public Role setId(Long id) {
        this.id = id;
        return this;
    }

    public String getRole() {
        return role;
    }

    public Role setRole(String role) {
        this.role = role;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Role setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public Role setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
        return this;
    }

    public Boolean getAvailable() {
        return available;
    }

    public Role setAvailable(Boolean available) {
        this.available = available;
        return this;
    }
}
