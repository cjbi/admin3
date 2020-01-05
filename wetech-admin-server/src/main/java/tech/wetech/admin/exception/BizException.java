package tech.wetech.admin.exception;

import tech.wetech.admin.model.ResultStatus;

public class BizException extends RuntimeException {

    private final String code;

    private final String msg;

    public BizException(ResultStatus resultStatus) {
        super(resultStatus.getMsg());
        this.code = resultStatus.getCode();
        this.msg = resultStatus.getMsg();
    }

    public BizException(ResultStatus resultStatus, String message) {
        super(message);
        this.code = resultStatus.getCode();
        this.msg = message;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
