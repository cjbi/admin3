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
    private Boolean available = Boolean.FALSE;

    /**
     * 叶子节点
     */
    private Boolean leaf = Boolean.FALSE;
    /**
     * 排序
     */
    private Long priority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Boolean getLeaf() {
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
    }

    public String makeSelfAsParentIds() {
        return getParentIds() + getId() + "/";
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }

}
