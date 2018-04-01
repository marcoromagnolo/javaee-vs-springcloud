package jeevsspring.spring.backoffice.operator.service;

public class InvalidSessionException extends AbstractServiceException {

    public InvalidSessionException() {
        super(ErrorCode.INVALID_SESSION);
    }
}
