package tech.wetech.admin.model.system;

import tech.wetech.admin.common.base.ResultCodeEnum;

public class BizException extends RuntimeException {

    private String code;

    private String msg;

    public BizException(String msg) {
        super(msg);
    }

    public BizException(ResultCodeEnum resultCode) {
        this(resultCode.getCode(), resultCode.getMsg());
    }

    public BizException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
