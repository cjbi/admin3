package tech.wetech.admin.modules.system.query;

import tech.wetech.admin.core.utils.BaseQuery;
import tech.wetech.admin.modules.system.po.Role;

/**
 * @author cjbi
 */
public class RoleQuery extends BaseQuery<Role> {

    /**
     * 角色标识 程序中判断使用,如"admin"
     */
    private String role;
    /**
     * 角色描述,UI界面显示使用
     */
    private String description;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
