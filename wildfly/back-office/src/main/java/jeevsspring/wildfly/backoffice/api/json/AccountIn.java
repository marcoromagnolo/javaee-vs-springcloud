package jeevsspring.wildfly.backoffice.api.json;

/**
 * @author Marco Romagnolo
 */
public class AccountIn extends SessionIn {

    private String playerId;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    @Override
    public String toString() {
        return "AccountIn{" +
                "playerId='" + playerId + '\'' +
                "} " + super.toString();
    }
}
