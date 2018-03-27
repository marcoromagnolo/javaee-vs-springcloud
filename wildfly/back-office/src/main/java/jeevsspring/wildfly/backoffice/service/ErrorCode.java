package jeevsspring.wildfly.backoffice.service;

/**
 * @author Marco Romagnolo
 */
public enum ErrorCode {

    AUTH_ERROR("Authentication Error"),
    BACKOFFICE_ERROR("Back Office Server Error"),
    INSUFFICIENT_FUNDS("Fondo insufficiente"),
    INVALID_AMOUNT("Invalid amount"),
    PASSWORD_ENCRYPTION_ERROR("Password Encryption Error"),
    INVALID_CREDENTIALS("Invalid Username and Password"),
    INVALID_SESSION("Invalid Session"),
    INCONSISTENT_DATA("Inconsistent in database");

    private final String message;

    ErrorCode(String s) {
        this.message = s;
    }

    public String getMessage() {
        return message;
    }
}
