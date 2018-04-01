package jeevsspring.spring.backoffice.player.api.json;

/**
 * @author Marco Romagnolo
 */
public class LogoutOut extends Status {

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
        return "LogoutOut{" +
                "playerId='" + playerId + '\'' +
                ", message='" + message + '\'' +
                "} " + super.toString();
    }
}
