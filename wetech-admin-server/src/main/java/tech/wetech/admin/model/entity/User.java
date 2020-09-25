package tech.wetech.admin.model.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author cjbi
 */
@Table(name = "sys_user")
@Data
public class User {

    /**
     * 编号
     */
    @Id
    @GeneratedValue
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 加密密码的盐
     */
    private String salt;
    /**
     * 拥有的角色列表
     */
    private String roleIds;

    private Integer locked;

    public String getCredentialsSalt() {
        return username + salt;
    }

}
