package jeevsspring.spring.console.bo.json;

/**
 * @author Marco Romagnolo
 */
public class OperatorLogoutIn extends SessionIn {

    private String operatorId;

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }
}
