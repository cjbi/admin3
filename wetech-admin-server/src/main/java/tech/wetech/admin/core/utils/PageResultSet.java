package tech.wetech.admin.core.utils;

import java.io.Serializable;
import java.util.List;

/**
 * 通用分页返回结果集
 *
 * @author cjbi
 */
public class PageResultSet<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 总数
     */
    private long total;
    /**
     * 返回的行数
     */
    private List<T> rows;


    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
