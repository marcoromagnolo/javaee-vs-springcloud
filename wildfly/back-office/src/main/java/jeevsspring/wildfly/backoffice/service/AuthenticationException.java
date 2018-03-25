package jeevsspring.wildfly.backoffice.service;

/**
 * @author Marco Romagnolo
 */
public class AuthenticationException extends AbstractServiceException {


    public AuthenticationException() {
        super(ErrorCode.INVALID_CREDENTIALS);
    }
}
