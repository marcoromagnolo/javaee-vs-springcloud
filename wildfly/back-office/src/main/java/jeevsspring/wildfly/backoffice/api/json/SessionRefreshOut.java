package jeevsspring.wildfly.backoffice.api.json;

/**
 * @author Marco Romagnolo
 */
public class SessionRefreshOut extends SessionOut {

    private String playerId;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    @Override
    public String toString() {
        return "SessionRefreshOut{" +
                "playerId='" + playerId + '\'' +
                "} " + super.toString();
    }
}
