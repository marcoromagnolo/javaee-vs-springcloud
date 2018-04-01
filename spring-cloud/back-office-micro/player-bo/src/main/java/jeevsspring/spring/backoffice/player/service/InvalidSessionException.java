package jeevsspring.spring.backoffice.player.service;

public class InvalidSessionException extends AbstractServiceException {

    public InvalidSessionException() {
        super(ErrorCode.INVALID_SESSION);
    }
}
