package tech.wetech.admin.core.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * 基础传输对象
 *
 * @author cjbi
 */
public class BaseQuery<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 排序字段
     */
    @JsonIgnore
    private String sort;
    /**
     * 升序、降序
     */
    @JsonIgnore
    private String order;

    /**
     * 数据库排序
     */
    @JsonIgnore
    private String orderBy;
    /**
     * 偏移量
     */
    @JsonIgnore
    private int offset = 0;
    /**
     * 条数
     */
    @JsonIgnore
    private int limit = 10;

    /**
     * 创建一个Class的对象来获取泛型的class
     */
    private Class<?> clz;

    public Class<?> getClz() {
        if(clz==null) {
            //获取泛型的Class对象
            clz = ((Class<?>)
                    (((ParameterizedType)(this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
        }
        return clz;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
        if (sort != null && sort.length() > 0 && this.order != null) {
            EntityColumn entityColumn = EntityHelper.getColumns(getClz()).stream().filter(column -> column.getProperty().equals(sort)).findFirst().orElse(null);
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
