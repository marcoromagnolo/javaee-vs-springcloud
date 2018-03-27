package jeevsspring.wildfly.backoffice.service;

public class InconsistentDataException extends AbstractServiceException {

    public InconsistentDataException() {
        super(ErrorCode.INCONSISTENT_DATA);
    }

    public InconsistentDataException(String message) {
        super(ErrorCode.INCONSISTENT_DATA, message);
    }
}
