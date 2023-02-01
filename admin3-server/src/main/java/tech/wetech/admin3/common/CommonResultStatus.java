package tech.wetech.admin3.common;

/**
 * @author cjbi
 */
public enum CommonResultStatus implements ResultStatus {
  OK(1000, "成功"),

  FAIL(1001, "失败"),

  PARAM_ERROR(1002, "参数非法"),

  RECORD_NOT_EXIST(1003, "记录不存在"),

  UNAUTHORIZED(1004, "未授权"),

  FORBIDDEN(1005, "禁止访问"),

  SERVER_ERROR(-1, "服务器内部错误");

  private final int code;
  private final String message;

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
