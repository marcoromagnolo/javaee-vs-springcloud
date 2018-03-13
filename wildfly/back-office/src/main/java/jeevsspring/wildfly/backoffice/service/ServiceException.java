package jeevsspring.wildfly.backoffice.service;

/**
 * @author Marco Romagnolo
 */
public class ServiceException extends Exception {

    private String errorCode;

    public ServiceException(String message, ErrorType errorCode) {
        super(message);
        this.errorCode = errorCode.name();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
