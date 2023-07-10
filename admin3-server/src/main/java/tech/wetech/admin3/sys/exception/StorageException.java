package tech.wetech.admin3.sys.exception;

import tech.wetech.admin3.common.BusinessException;
import tech.wetech.admin3.common.ResultStatus;

/**
 * @author cjbi
 */
public class StorageException extends BusinessException {

  public StorageException(ResultStatus status) {
    super(status);
  }

  public StorageException(ResultStatus status, String message) {
    super(status, message);
  }
}
