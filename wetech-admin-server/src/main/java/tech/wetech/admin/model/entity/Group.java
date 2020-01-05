package tech.wetech.admin.model.entity;

import lombok.Data;
import tech.wetech.admin.model.enumeration.GroupType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author cjbi
 */
@Table(name = "sys_group")
@Data
public class Group {

    /**
     * 编号
     */
    @Id
    @Column(name = "id")
    private Long id;
    /**
     * 组名称
     */
    @NotBlank(message = "组名称不能为空")
    private String name;

    /**
     * 组类型
     */
    @NotNull(message = "组类型不能为空")
    private GroupType type;
}
