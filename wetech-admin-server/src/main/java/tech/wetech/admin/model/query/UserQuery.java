package tech.wetech.admin.model.query;

import lombok.Data;

/**
 * @author cjbi
 */
@Data
public class UserQuery extends PageQuery {
    private Long id;
    private String username;
    private Integer locked;

}
