package jeevsspring.wildfly.backoffice.service;

/**
 * @author Marco Romagnolo
 */
public enum ErrorType {

    AUTH_ERROR("Authentication Error"),
    DATABASE_ERROR("Database Error"),
    INSUFFICIENT_FUNDS("Fondo insufficiente"),
    INVALID_AMOUNT("Invalid amount"),
    PASSWORD_ENCRYPTION_ERROR("Password Encryption Error"),
    INVALID_CREDENTIALS("Invalid Username and Password");

    private final String message;

    ErrorType(String s) {
        this.message = s;
    }

    public String getMessage() {
        return message;
    }
}
