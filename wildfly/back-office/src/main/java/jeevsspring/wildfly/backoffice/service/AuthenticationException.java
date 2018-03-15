package jeevsspring.wildfly.backoffice.service;

/**
 * @author Marco Romagnolo
 */
public class AuthenticationException extends Exception {

    private String errorCode = ErrorType.INVALID_CREDENTIALS.name();

    public AuthenticationException() {
        super(ErrorType.INVALID_CREDENTIALS.getMessage());
    }

    public String getErrorCode() {
        return errorCode;
    }
}
