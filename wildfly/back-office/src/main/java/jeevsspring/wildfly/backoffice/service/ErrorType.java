package jeevsspring.wildfly.backoffice.service;

/**
 * @author Marco Romagnolo
 */
public enum ErrorType {

    AUTH_ERROR("Authentication Error"),
    DATABASE_ERROR("Database Error"),
    INSUFFICIENT_FUNDS("Fondo insufficiente"),
    INVALID_AMOUNT("Invalid amount");

    private final String message;

    ErrorType(String s) {
        this.message = s;
    }

    public String getMessage() {
        return message;
    }
}
