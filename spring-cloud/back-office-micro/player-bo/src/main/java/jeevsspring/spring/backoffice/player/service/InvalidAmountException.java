package jeevsspring.spring.backoffice.player.service;

public class InvalidAmountException extends AbstractServiceException {

    public InvalidAmountException() {
        super(ErrorCode.INVALID_AMOUNT);
    }
}
