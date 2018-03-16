package jeevsspring.wildfly.backoffice.api.json;

/**
 * @author Marco Romagnolo
 */
public class SessionRefreshIn extends SessionIn {

    private String playerId;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    @Override
    public String toString() {
        return "SessionRefreshIn{" +
                "playerId='" + playerId + '\'' +
                "} " + super.toString();
    }
}
