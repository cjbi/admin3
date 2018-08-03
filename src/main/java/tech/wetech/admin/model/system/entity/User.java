package tech.wetech.admin.model.system.entity;

import org.springframework.util.StringUtils;
import tech.wetech.admin.model.BaseEntity;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cjbi
 */
@Table(name = "sys_user")
public class User extends BaseEntity {
    /**
     * 所属公司
     */
    @NotNull(message = "所属组织不能为空")
    private Long organizationId;
    /**
     * 用户名
     */
    @NotNull(message = "用户名不能为空")
    private String username;
    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    private String password;
    /**
     * 加密密码的盐
     */
    private String salt;
    /**
     * 拥有的角色列表
     */
    private String roleIds;

    @Transient
    private List<Long> roleIdList;

    private String groupIds;

    @Transient
    private List<Long> groupIdList;

    private Boolean locked = Boolean.FALSE;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCredentialsSalt() {
        return username + salt;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        String[] roleIdStrs = roleIds.split(",");
        for (String roleId : roleIdStrs) {
            if (StringUtils.isEmpty(roleId)) {
                continue;
            }
            getRoleIdList().add(Long.valueOf(roleId));
        }
        this.roleIds = roleIds;
    }

    public List<Long> getRoleIdList() {
        if (roleIdList == null) {
            roleIdList = new ArrayList<>();
        }
        return roleIdList;
    }

    public void setRoleIdList(List<Long> roleIdList) {
        StringBuilder s = new StringBuilder();
        for (Long roleId : roleIdList) {
            s.append(roleId);
            s.append(",");
        }
        if (s.length() > 0) {
            s.deleteCharAt(s.length() - 1);
        }
        this.roleIds = s.toString();
        this.roleIdList = roleIdList;
    }

    public String getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String groupIds) {
        String[] groupIdStrs = groupIds.split(",");
        for (String groupId : groupIdStrs) {
            if (StringUtils.isEmpty(groupId)) {
                continue;
            }
            getGroupIdList().add(Long.valueOf(groupId));
        }
        this.groupIds = groupIds;
    }

    public List<Long> getGroupIdList() {
        if (groupIdList == null) {
            groupIdList = new ArrayList<>();
        }
        return groupIdList;
    }

    public void setGroupIdList(List<Long> groupIdList) {
        StringBuilder s = new StringBuilder();
        for (Long groupId : groupIdList) {
            s.append(groupId);
            s.append(",");
        }
        if (s.length() > 0) {
            s.deleteCharAt(s.length() - 1);
        }
        this.groupIds = s.toString();
        this.groupIdList = groupIdList;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

}
