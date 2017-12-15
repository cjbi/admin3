package tech.wetech.admin.web.dto;

import java.util.HashMap;
import java.util.List;

/**
 * 将数据封装为map，以便传到前台供datatables读取数据；支持后台分页
 * 
 * @author cjbi
 * @date 2017/12/16
 * @version 1.0.0
 */
public class DataTableModel<T> {

    private int start = 0;

    private int length = 10;

    private int draw;

    private List<T> data;

    private long recordsTotal;

    private String keywords;// 模糊匹配关键字

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public DataTableModel() {

    }

    public DataTableModel(int start, int length, int draw) {
        this(start, length);
        this.draw = draw;
    }

    public DataTableModel(int start, int length) {
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

    public void setData(List<T> data) {
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public long getRecordsFiltered() {
        return recordsTotal;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }
}
