package jeevsspring.spring.backoffice.operator.service;

/**
 * @author Marco Romagnolo
 */
public class AuthenticationException extends AbstractServiceException {


    public AuthenticationException() {
        super(ErrorCode.INVALID_CREDENTIALS);
    }
}
