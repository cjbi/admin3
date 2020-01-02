package tech.wetech.admin.modules.system.po;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author cjbi
 */
@Table(name = "sys_log")
@Data
public class Log {

    /**
     * 编号
     */
    @Id
    @Column(name = "id")
    private Long id;
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户ip
     */
    private String ip;

    /**
     * 请求方法
     */
    private String reqMethod;

    /**
     * 执行方法
     */
    private String execMethod;

    /**
     * 响应时间
     */
    private Long execTime;

    /**
     * 描述
     */
    private String execDesc;

    /**
     * 状态
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;


    /**
     * 请求URL
     */
    private String reqUri;

    /**
     * 参数
     */
    private String args;

    /**
     * 返回值
     */
    private String returnVal;
}