package tech.wetech.admin.common.base;

/**
 * @author cjbi
 */
public enum ResultCodeEnum {

    OK("200","处理成功"),
    BadRequest("400","请求参数有误"),
    Unauthorized("401","未授权"),
    ParamsMiss("483","缺少接口中必填参数"),
    ParamError("484","参数非法"),
    FailedDelOwn("485","不能删除自己"),
    InternalServerError("500","服务器内部错误"),
    NotImplemented("501","业务异常");

    private String code;
    private String msg;

    private ResultCodeEnum(String code, String msg) {
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
