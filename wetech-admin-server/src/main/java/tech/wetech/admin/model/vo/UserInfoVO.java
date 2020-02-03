package tech.wetech.admin.model.vo;

import lombok.Data;

import java.util.Set;

/**
 * @author cjbi
 */
@Data
public class UserInfoVO {
    private String name;
    private String avatar;
    private Set<String> permissions;

}
