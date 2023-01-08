package tech.wetech.admin3.exception;

/**
 * @author cjbi
 */
public class BusinessException extends RuntimeException {
    private final ResultStatus status;

    public BusinessException(ResultStatus status) {
        this.status = status;
    }

    public BusinessException(ResultStatus status, String message) {
        super(message);
        this.status = status;
    }

    public ResultStatus getStatus() {
        return status;
    }


}
