package tech.wetech.admin.model.system.entity;

import org.apache.ibatis.type.JdbcType;
import tech.wetech.admin.model.BaseEntity;
import tk.mybatis.mapper.annotation.ColumnType;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author cjbi
 */
@Table(name = "sys_group")
public class Group extends BaseEntity {

    /**
     * 组名称
     */
    @NotNull(message = "组名称不能为空")
    private String name;

    /**
     * 组类型
     */
    @NotNull(message = "组类型不能为空")
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private GroupType type;

    /**
     * 描述
     */
    private String description;

    public static enum GroupType {
        GROUP_1,
        GROUP_2,
        GROUP_3;
        //···相比用户角色管理，可以垂直粒度进行用户管理
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GroupType getType() {
        return type;
    }

    public void setType(GroupType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
