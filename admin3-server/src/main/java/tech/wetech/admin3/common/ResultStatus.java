package tech.wetech.admin3.common;

/**
 * @author cjbi
 */
public interface ResultStatus {
  /**
   * 错误码
   */
  int getCode();

  /**
   * 错误信息
   */
  String getMessage();
}
