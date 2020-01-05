package tech.wetech.admin.model.enumeration;

import tech.wetech.admin.model.ResultStatus;

/**
 * @author cjbi@outlook.com
 */
public enum CommonResultStatus implements ResultStatus {

    OK("200", "处理成功"),
    PARAM_ERROR("603", "参数非法"),
    INTERNAL_SERVER_ERROR("606", "服务器内部错误"),
    RECORD_NOT_EXIST("608", "记录不存在"),
    REMOTE_SERVICE_ERROR("609", "远程服务调用失败"),
    FAILED_DEL_OWN("485", "不能删除自己"),
    FAILED_USER_ALREADY_EXIST("486", "该用户已存在"),
    ;

    private String code;
    private String msg;

    CommonResultStatus(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

}
