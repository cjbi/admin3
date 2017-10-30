package tech.wetech.admin.web.dto;

public class Page {

    private int pageSize = 15;

    private int pageNum = 1;

    private int limit;

    private int offset;

    private Object result;

    private long total;

    public Page() {
        this.offset = (this.pageNum - 1) * pageSize;
        this.limit = pageSize;
    }

    public Page(int start, int length) {
        this.offset = (start - 1) * length;
        this.limit = start;
        this.pageNum = start;
        this.pageSize = length;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
