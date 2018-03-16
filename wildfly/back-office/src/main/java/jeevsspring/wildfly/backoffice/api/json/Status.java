package jeevsspring.wildfly.backoffice.api.json;

import jeevsspring.wildfly.backoffice.service.ErrorCode;

/**
 * @author Marco Romagnolo
 */
public class Status {

    private boolean error;

    private String errorCode;

    private String message;

    public Status() {
    }

    public Status(ErrorCode errorCode) {
        this.errorCode = errorCode.name();
        this.error = true;
        this.message = errorCode.getMessage();
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Status{" +
                "error=" + error +
                ", errorCode='" + errorCode + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
