package tech.wetech.admin.model.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * @author cjbi
 */
@Table(name = "sys_role")
@Data
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
}
