package tech.wetech.admin.model.system;

import java.io.Serializable;

/**
 * @author 
 */
public class LogWithBLOBs extends Log implements Serializable {
    /**
     * 请求URL
     */
    private String reqUri;

    /**
     * 返回值
     */
    private String returnVal;

    private static final long serialVersionUID = 1L;

    public String getReqUri() {
        return reqUri;
    }

    public void setReqUri(String reqUri) {
        this.reqUri = reqUri;
    }

    public String getReturnVal() {
        return returnVal;
    }

    public void setReturnVal(String returnVal) {
        this.returnVal = returnVal;
    }
}