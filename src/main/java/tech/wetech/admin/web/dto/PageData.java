package tech.wetech.admin.web.dto;

/**
 * @author cjbi
 * @param <T>
 */
public class PageData<T> {

    private int start = 0;

    private int length =10;

    private T result;//返回

    private long total;

    private String keywords;//模糊匹配关键字

    public PageData() {
    }

    public PageData(int start, int length) {
        this.start = start;
        this.length = length;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

}
