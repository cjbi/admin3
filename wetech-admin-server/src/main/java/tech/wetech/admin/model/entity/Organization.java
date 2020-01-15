package tech.wetech.admin.model.entity;

import lombok.Data;

import javax.persistence.Table;

/**
 * @author cjbi
 */
@Table(name = "sys_organization")
@Data
public class Organization {

    /**
     * 编号
     */
    private Long id;
    /**
     * 组织机构名称
     */
    private String name;
    /**
     * 父编号
     */
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
