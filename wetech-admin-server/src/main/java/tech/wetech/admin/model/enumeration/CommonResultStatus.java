package tech.wetech.admin.model.enumeration;

import tech.wetech.admin.model.ResultStatus;

/**
 * @author cjbi@outlook.com
 */
public enum CommonResultStatus implements ResultStatus {

    OK(1000, "成功"),
    PARAM_ERROR(1001, "参数非法"),
    LOGIN_ERROR(1002, "登录失败"),
    INTERNAL_SERVER_ERROR(1003, "服务异常"),
    RECORD_NOT_EXIST(1005, "记录不存在"),
    FAILED_DEL_OWN(1006, "不能删除自己"),
    FAILED_USER_ALREADY_EXIST(1007, "该用户已存在"),
    FAILED_LOCK_OWN(1008,"不能禁用自己"),
    UNAUTHORIZED(1009,"未授权")
    ;

    private int code;
    private String message;

    CommonResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
