package jeevsspring.wildfly.backoffice.service;

/**
 * @author Marco Romagnolo
 */
public class AuthenticationException extends Exception {

    private ErrorCode errorCode = ErrorCode.INVALID_CREDENTIALS;

    public AuthenticationException() {
        super(ErrorCode.INVALID_CREDENTIALS.getMessage());
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
