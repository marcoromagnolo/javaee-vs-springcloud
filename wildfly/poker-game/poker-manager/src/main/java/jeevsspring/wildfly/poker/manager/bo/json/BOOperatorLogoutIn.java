package jeevsspring.wildfly.poker.manager.bo.json;

/**
 * @author Marco Romagnolo
 */
public class BOOperatorLogoutIn extends BOSessionIn {

    private String operatorId;

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    @Override
    public String toString() {
        return "BOOperatorLogoutIn{" +
                "operatorId='" + operatorId + '\'' +
                "} " + super.toString();
    }
}
