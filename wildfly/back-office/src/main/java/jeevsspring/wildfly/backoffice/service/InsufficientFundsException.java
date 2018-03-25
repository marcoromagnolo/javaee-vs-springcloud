package jeevsspring.wildfly.backoffice.service;

public class InsufficientFundsException extends AbstractServiceException {

    public InsufficientFundsException() {
        super(ErrorCode.INSUFFICIENT_FUNDS);
    }
}
