package tech.wetech.admin.model.system;

import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cjbi
 */
public class User implements Serializable{
    /**
     * 编号
     */
    private Long id;
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
    @Null(message = "测试测试测试")
    private String roleIds;
    private List<Long> roleIdList;
    private Boolean locked = Boolean.FALSE;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if(s.length() >0) {
            s.deleteCharAt(s.length()-1);
        }
        this.roleIds = s.toString();
        this.roleIdList = roleIdList;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

}
