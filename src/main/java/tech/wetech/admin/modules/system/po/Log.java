package tech.wetech.admin.modules.system.po;

import javax.persistence.*;
import java.util.Date;

/**
 * @author cjbi
 */
@Table(name="sys_log")
public class Log {

    /**
     * 编号
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReqUri() {
        return reqUri;
    }

    public void setReqUri(String reqUri) {
        this.reqUri = reqUri;
    }

    public String getArgs() {
        return args;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public String getReturnVal() {
        return returnVal;
    }

    public void setReturnVal(String returnVal) {
        this.returnVal = returnVal;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getReqMethod() {
        return reqMethod;
    }

    public void setReqMethod(String reqMethod) {
        this.reqMethod = reqMethod;
    }

    public String getExecMethod() {
        return execMethod;
    }

    public void setExecMethod(String execMethod) {
        this.execMethod = execMethod;
    }

    public Long getExecTime() {
        return execTime;
    }

    public void setExecTime(Long execTime) {
        this.execTime = execTime;
    }

    public String getExecDesc() {
        return execDesc;
    }

    public void setExecDesc(String execDesc) {
        this.execDesc = execDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}