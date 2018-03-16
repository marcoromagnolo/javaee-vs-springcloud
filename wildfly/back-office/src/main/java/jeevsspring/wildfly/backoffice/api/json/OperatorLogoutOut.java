package jeevsspring.wildfly.backoffice.api.json;

/**
 * @author Marco Romagnolo
 */
public class OperatorLogoutOut extends Status {

    private String operatorId;

    private String message;

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "OperatorLogoutOut{" +
                "operatorId='" + operatorId + '\'' +
                ", message='" + message + '\'' +
                "} " + super.toString();
    }
}
