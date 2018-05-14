package tech.wetech.admin.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 基础信息
 *
 * @author liuzh
 * @since 2016-01-31 21:42
 */
public class BaseEntity implements Serializable {

    /**
     * 编号
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private Integer page = 1;

    @Transient
    private Integer rows = 10;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
}
