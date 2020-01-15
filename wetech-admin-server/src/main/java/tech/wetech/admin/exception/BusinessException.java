package tech.wetech.admin.exception;

import tech.wetech.admin.model.ResultStatus;

public class BusinessException extends RuntimeException {

    ResultStatus status;

    public BusinessException(ResultStatus status) {
        //不生成栈追踪信息
        super(status.getMessage(), null, false, false);
        this.status = status;
    }

    public BusinessException(ResultStatus status, String message) {
        super(message);
        this.status = status;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }


}
