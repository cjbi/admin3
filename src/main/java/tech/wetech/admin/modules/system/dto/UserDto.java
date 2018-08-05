package tech.wetech.admin.modules.system.dto;

import tech.wetech.admin.modules.system.po.User;

import java.util.List;

public class UserDto {

    private Long id; //编号
    private Long organizationId; //所属公司
    private String username; //用户名
    private String roleIds; //拥有的角色列表
    private List<Long> roleIdList;
    private String roleNames;
    private String groupIds;
    private List<Long> groupIdList;
    private String groupNames;

    private Boolean locked;
    private String organizationName;

    public UserDto() {

    }

    public UserDto(User user) {
        this.id = user.getId();
        this.organizationId = user.getOrganizationId();
        this.username = user.getUsername();
        this.roleIds = user.getRoleIds();
        this.roleIdList = user.getRoleIdList();
        this.groupIds = user.getGroupIds();
        this.groupIdList = user.getGroupIdList();
        this.locked = user.getLocked();
    }

    public String getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(String groupIds) {
        this.groupIds = groupIds;
    }

    public List<Long> getGroupIdList() {
        return groupIdList;
    }

    public void setGroupIdList(List<Long> groupIdList) {
        this.groupIdList = groupIdList;
    }

    public String getGroupNames() {
        return groupNames;
    }

    public void setGroupNames(String groupNames) {
        this.groupNames = groupNames;
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

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public List<Long> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Long> roleIdList) {
        this.roleIdList = roleIdList;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }
}
