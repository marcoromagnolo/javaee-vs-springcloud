package jeevsspring.wildfly.backoffice.service;

/**
 * @author Marco Romagnolo
 */
public class ServiceException extends Exception {

    private String errorCode;

    public ServiceException(ErrorType errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode.name();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
