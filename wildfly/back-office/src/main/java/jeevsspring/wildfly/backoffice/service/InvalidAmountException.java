package jeevsspring.wildfly.backoffice.service;

public class InvalidAmountException extends AbstractServiceException {

    public InvalidAmountException() {
        super(ErrorCode.INVALID_AMOUNT);
    }
}
