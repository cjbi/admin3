package tech.wetech.admin.modules.base.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 分页查询
 */
@ApiModel("分页查询")
public class PageQuery implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 页码，从1开始
     */
    @ApiModelProperty("页码，从1开始")
    private int pageNum;
    /**
     * 页面大小
     */
    @ApiModelProperty("页面大小")
    private int pageSize;
    /**
     * 包含count查询
     */
    @ApiModelProperty("包含count查询")
    private boolean countSql = true;
    /**
     * 分页合理化参数，默认值为false。当该参数设置为 true 时，pageNum<=0 时会查询第一页， pageNum>pages（超过总数时），会查询最后一页。默认false 时，直接根据参数进行查询。
     */
    @ApiModelProperty("分页合理化参数，默认值为false。当该参数设置为 true 时，pageNum<=0 时会查询第一页， pageNum>pages（超过总数时），会查询最后一页。默认false 时，直接根据参数进行查询。")
    private boolean reasonable;
    /**
     * 当设置为true的时候，如果pagesize设置为0（或RowBounds的limit=0），就不执行分页，返回全部结果
     */
    @ApiModelProperty("当设置为true的时候，如果pagesize设置为0（或RowBounds的limit=0），就不执行分页，返回全部结果")
    private boolean pageSizeZero = true;
    /**
     * 排序
     */
    @ApiModelProperty("排序")
    private String orderBy;

    public int getPageNum() {
        return pageNum;
    }

    public PageQuery setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public PageQuery setPageNumber(int pageNumber) {
        this.pageNum = pageNumber;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public PageQuery setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public boolean isCountSql() {
        return countSql;
    }

    public PageQuery setCountSql(boolean countSql) {
        this.countSql = countSql;
        return this;
    }

    public boolean isReasonable() {
        return reasonable;
    }

    public PageQuery setReasonable(boolean reasonable) {
        this.reasonable = reasonable;
        return this;
    }

    public boolean isPageSizeZero() {
        return pageSizeZero;
    }

    public PageQuery setPageSizeZero(boolean pageSizeZero) {
        this.pageSizeZero = pageSizeZero;
        return this;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public PageQuery setOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    @Override
    public String toString() {
        return "PageQuery{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", countSql=" + countSql +
                ", reasonable=" + reasonable +
                ", pageSizeZero=" + pageSizeZero +
                ", orderBy='" + orderBy + '\'' +
                '}';
    }
}
