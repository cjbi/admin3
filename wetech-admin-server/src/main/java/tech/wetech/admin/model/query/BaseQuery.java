package tech.wetech.admin.model.query;

import lombok.Data;

/**
 * @author cjbi
 */
@Data
public class BaseQuery {
    private int pageNo;
    private int pageSize;
    private String sortField;
    private SortOrder sortOrder;
    public static enum SortOrder {
        ascend,
        descend
    }
}
