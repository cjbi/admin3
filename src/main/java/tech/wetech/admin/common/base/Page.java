package tech.wetech.admin.common.base;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author cjbi
 */
public class Page implements Serializable{

    private static final long serialVersionUID = 1L;

    /**
     * 排序字段
     */
    private String sort;
    /**
     * 升序、降序
     */
    private Order order;
    /**
     * 搜索
     */
    private String search;
    /**
     * 偏移量
     */
    private int offset = 0;
    /**
     * 条数
     */
    private int limit = 10;

    public enum Order {
        asc, desc;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(String order) {
        boolean contain = Arrays.stream(Order.values()).anyMatch(item -> item.name().equals(order));
        if (contain) {
            this.order = Order.valueOf(order);
        }
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
