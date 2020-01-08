package tech.wetech.admin.model;

import tech.wetech.mybatis.domain.Page;

import java.io.Serializable;
import java.util.List;

/**
 * @author cjbi
 */
public class PageWrapper implements Serializable {

    private static final long serialVersionUID = 1L;

    private List list;

    private long total;

    public PageWrapper() {
    }

    public PageWrapper(Page page) {
        this.list = page;
        this.total = page.getTotal();
    }

    public PageWrapper(List list, long total) {
        this.list = list;
        this.total = total;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
