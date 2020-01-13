package tech.wetech.admin.model.query;

import lombok.Data;

/**
 * @author cjbi
 */
@Data
public class UserQuery {
    private Long id;
    private String username;
    private Integer locked;
    private int pageNo;
    private int pageSize;
}
