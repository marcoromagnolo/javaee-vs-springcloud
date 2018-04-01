package jeevsspring.spring.backoffice.player.service;

public class AbstractServiceException extends Exception {

    private ErrorCode errorCode;

    public AbstractServiceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public AbstractServiceException(ErrorCode errorCode, String message) {
        super(errorCode.getMessage() + ", " + message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
