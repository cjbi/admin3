package tech.wetech.admin.core.exception;

import tech.wetech.admin.core.utils.ResultCodeEnum;

public class BizException extends RuntimeException {

    private String code;

    private String msg;

    public BizException(String msg) {
        super(msg);
    }

    public BizException(ResultCodeEnum resultCodeEnum) {
        this(resultCodeEnum.getCode(), resultCodeEnum.getMsg());
    }

    public BizException(ResultCodeEnum resultCodeEnum, String msg) {
        super(msg);
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getCode();
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
