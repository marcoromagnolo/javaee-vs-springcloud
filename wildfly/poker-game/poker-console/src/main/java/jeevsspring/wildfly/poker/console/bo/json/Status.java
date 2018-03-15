package jeevsspring.wildfly.poker.console.bo.json;

/**
 * @author Marco Romagnolo
 */
public class Status {

    private boolean error;

    private String errorCode;

    private String message;

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
}
