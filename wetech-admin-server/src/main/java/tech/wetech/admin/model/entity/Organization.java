package tech.wetech.admin.model.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author cjbi
 */
@Table(name = "sys_organization")
@Data
public class Organization {

    /**
     * 编号
     */
    @Id
    @Column(name = "id")
    private Long id;
    /**
     * 组织机构名称
     */
    @NotBlank(message = "组织机构名称不能为空")
    private String name;
    /**
     * 父编号
     */
    @NotNull(message = "父编号不能为空")
    private Long parentId;
    /**
     * 父编号列表，如1/2/
     */
    private String parentIds;
    private Boolean available;

    /**
     * 叶子节点
     */
    private Boolean leaf;
    /**
     * 排序
     */
    private Long priority;

    public String makeSelfAsParentIds() {
        return getParentIds() + getId() + "/";
    }

}
