package tech.wetech.admin.model.dto;

import lombok.Data;

/**
 * @author cjbi
 */
@Data
public class UserPageDTO {
    private Long id; //编号
    private String username; //用户名
    private String roleIds; //拥有的角色列表
    private String roleNames;
    private Boolean locked;
}
