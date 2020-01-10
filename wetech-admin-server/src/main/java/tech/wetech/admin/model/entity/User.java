package tech.wetech.admin.model.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author cjbi
 */
@Table(name = "sys_user")
@Data
public class User implements Serializable {

    /**
     * 编号
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = UserUpdateChecks.class)
    private Long id;
    /**
     * 所属公司
     */
    @NotNull(message = "所属组织不能为空")
    private Long organizationId;
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Min(value = 3, message = "用户名不能低于3位")
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空", groups = UserCreateChecks.class)
    @Min(value = 6, message = "密码不能低于6位", groups = UserCreateChecks.class)
    private String password;
    /**
     * 加密密码的盐
     */
    private String salt;
    /**
     * 拥有的角色列表
     */
    private String roleIds;

    private String groupIds;

    private Boolean locked;

    public interface UserCreateChecks {

    }

    public interface UserUpdateChecks {

    }

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getCredentialsSalt() {
        return username + salt;
    }

}
