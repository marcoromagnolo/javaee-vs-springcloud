package jeevsspring.spring.backoffice.player.service;

/**
 * @author Marco Romagnolo
 */
public class AuthenticationException extends AbstractServiceException {


    public AuthenticationException() {
        super(ErrorCode.INVALID_CREDENTIALS);
    }
}
