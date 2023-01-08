package tech.wetech.admin3.exception;

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
