package jeevsspring.wildfly.backoffice.service;

/**
 * @author Marco Romagnolo
 */
public class BackOfficeException extends Exception {

    private ErrorCode errorCode = ErrorCode.BACKOFFICE_ERROR;

    public BackOfficeException() {
        super(ErrorCode.BACKOFFICE_ERROR.getMessage());
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
