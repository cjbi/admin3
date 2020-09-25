package tech.wetech.admin.model.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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
    @GeneratedValue
    private Long id;
    /**
     * 角色标识 程序中判断使用,如"admin"
     */
    private String role;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 角色描述,UI界面显示使用
     */
    private String description;
    /**
     * 状态,如果不可用将不会添加给用户。1.正常 0.禁用
     */
    private Integer status;
    /**
     * 拥有的资源
     */
    private String permissionIds;

}
