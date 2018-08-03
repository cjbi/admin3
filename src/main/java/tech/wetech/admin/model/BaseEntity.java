package tech.wetech.admin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 基础信息
 *
 * @author cjbi
 */
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 编号
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 排序字段
     */
    @Transient
    @JsonIgnore
    private String sort;
    /**
     * 升序、降序
     */
    @Transient
    @JsonIgnore
    private String order;

    /**
     * 数据库排序
     */
    @Transient
    @JsonIgnore
    private String orderBy;
    /**
     * 搜索
     */
    @Transient
    @JsonIgnore
    private String search;
    /**
     * 偏移量
     */
    @Transient
    @JsonIgnore
    private int offset = 0;
    /**
     * 条数
     */
    @Transient
    @JsonIgnore
    private int limit = 10;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
        if (sort != null && sort.length() > 0 && this.order != null) {
            EntityColumn entityColumn = EntityHelper.getColumns(this.getClass()).stream().filter(column -> column.getProperty().equals(sort)).findFirst().orElse(null);
            if (entityColumn != null) {
                this.orderBy = entityColumn.getColumn() + " " + this.order;
            }
        }
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
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
}
