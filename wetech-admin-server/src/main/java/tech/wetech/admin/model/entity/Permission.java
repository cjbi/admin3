package tech.wetech.admin.model.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
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
    @GeneratedValue
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
    /**
     * 图标
     */
    private String icon;
    /**
     * 配置,json格式
     */
    private String config;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 排序
     */
    private Long sort;


    public String makeSelfAsParentIds() {
        return getParentIds() + getId() + "/";
    }

}
