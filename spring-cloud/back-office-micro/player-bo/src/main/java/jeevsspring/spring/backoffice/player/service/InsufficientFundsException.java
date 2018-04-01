package jeevsspring.spring.backoffice.player.service;

public class InsufficientFundsException extends AbstractServiceException {

    public InsufficientFundsException() {
        super(ErrorCode.INSUFFICIENT_FUNDS);
    }
}
