package jeevsspring.wildfly.backoffice.service;

public class AbstractServiceException extends Exception {

    private ErrorCode errorCode;

    public AbstractServiceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
