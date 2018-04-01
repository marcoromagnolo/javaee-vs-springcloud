package jeevsspring.spring.backoffice.player.api.json;

import jeevsspring.spring.backoffice.player.service.ErrorCode;

/**
 * @author Marco Romagnolo
 */
public class Status {

    private String error;

    private String message;

    public Status() {
    }

    public Status(String message) {
        this.message = message;
    }

    public Status(ErrorCode error) {
        this.error = error.name();
        this.message = error.getMessage();
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
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
                "errorCode='" + error + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
