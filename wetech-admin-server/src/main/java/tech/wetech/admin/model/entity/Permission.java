package tech.wetech.admin.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author cjbi
 */
@Table(name = "sys_permission")
@Data
public class Permission {

    /**
     * 编号
     */
    @Id
    private Long id;

    /**
     * 资源名称
     */
    private String name;
    /**
     * 资源类型
     */
    private Integer type;
    /**
     * 资源路径
     */
    private String url;
    /**
     * 权限字符串
     */
    private String permission;
    /**
     * 父编号
     */
    private Long parentId;
    /**
     * 父编号列表
     */
    private String parentIds;
    private Integer status;
    /**
     * 图标
     */
    private String icon;
    /**
     * 排序
     */
    @Column(name = "`order`")
    private Long order;

    public String makeSelfAsParentIds() {
        return getParentIds() + getId() + "/";
    }

    public boolean isRootNode() {
        return parentId == 0;
    }
}
