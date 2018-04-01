package jeevsspring.spring.backoffice.operator.api.json;

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

    @Override
    public String toString() {
        return "OperatorLogoutIn{" +
                "operatorId='" + operatorId + '\'' +
                "} " + super.toString();
    }
}
