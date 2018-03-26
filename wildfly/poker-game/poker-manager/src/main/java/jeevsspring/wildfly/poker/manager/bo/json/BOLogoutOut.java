package jeevsspring.wildfly.poker.manager.bo.json;

/**
 * @author Marco Romagnolo
 */
public class BOLogoutOut extends BOStatus {

    private String playerId;

    private String message;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BOLogoutOut{" +
                "playerId='" + playerId + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
