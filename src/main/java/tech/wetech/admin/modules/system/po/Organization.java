package tech.wetech.admin.modules.system.po;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author cjbi
 */
@Table(name="sys_organization")
public class Organization {

    /**
     * 编号
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Long getId() {
        return id;
    }

    public Organization setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Organization setName(String name) {
        this.name = name;
        return this;
    }

    public Long getParentId() {
        return parentId;
    }

    public Organization setParentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getParentIds() {
        return parentIds;
    }

    public Organization setParentIds(String parentIds) {
        this.parentIds = parentIds;
        return this;
    }

    public Boolean getAvailable() {
        return available;
    }

    public Organization setAvailable(Boolean available) {
        this.available = available;
        return this;
    }

    public Boolean getLeaf() {
        return leaf;
    }

    public Organization setLeaf(Boolean leaf) {
        this.leaf = leaf;
        return this;
    }

    public Long getPriority() {
        return priority;
    }

    public Organization setPriority(Long priority) {
        this.priority = priority;
        return this;
    }

    public String makeSelfAsParentIds() {
        return getParentIds() + getId() + "/";
    }
}
