package tech.wetech.admin.model;

import tech.wetech.mybatis.domain.Page;

import java.io.Serializable;
import java.util.List;

/**
 * @author cjbi
 */
public class PageWrapper<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<T> data;

    private long totalCount;

    private int pageNo;

    private int pageSize;


    public PageWrapper() {
    }

    public PageWrapper(Page<T> page) {
        this.data = page;
        this.totalCount = page.getTotal();
        this.pageSize = page.getPageSize();
        this.pageNo = page.getPageNumber();
    }

    public PageWrapper(List<T> data, long totalCount, int pageNo, int pageSize) {
        this.data = data;
        this.totalCount = totalCount;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
}
