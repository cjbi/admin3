package tech.wetech.admin.model.entity;

import lombok.Data;
import tech.wetech.admin.model.enumeration.ResourceType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author cjbi
 */
@Table(name = "sys_resource")
@Data
public class Resource {

    /**
     * 编号
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "资源名称不能为空")
    /**
     * 资源名称
     */
    private String name;
    /**
     * 资源类型
     */
    @NotNull(message = "资源类型不能为空")
    private ResourceType type;
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
    @NotNull(message = "父编号不能为空")
    private Long parentId;
    /**
     * 父编号列表
     */
    private String parentIds;
    private Boolean available;
    /**
     * 图标
     */
    private String icon;
    /**
     * 排序
     */
    private Long priority;

    /**
     * 叶子节点
     */
    private Boolean leaf;

    public String makeSelfAsParentIds() {
        return getParentIds() + getId() + "/";
    }

    public boolean isRootNode() {
        return parentId == 0;
    }
}
