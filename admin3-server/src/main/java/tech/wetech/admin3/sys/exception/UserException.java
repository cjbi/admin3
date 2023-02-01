package tech.wetech.admin3.sys.exception;

import tech.wetech.admin3.common.BusinessException;
import tech.wetech.admin3.common.ResultStatus;

/**
 * @author cjbi
 */
public class UserException extends BusinessException {

  public UserException(ResultStatus status) {
    super(status);
  }

  public UserException(ResultStatus status, String message) {
    super(status, message);
  }
}
