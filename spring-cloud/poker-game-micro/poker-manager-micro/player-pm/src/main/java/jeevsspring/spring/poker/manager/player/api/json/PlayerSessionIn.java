package jeevsspring.spring.poker.manager.player.api.json;

/**
 * @author Marco Romagnolo
 */
public class PlayerSessionIn {

    private String sessionId;
    private String sessionToken;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    @Override
    public String toString() {
        return "PlayerSessionIn{" +
                "sessionId='" + sessionId + '\'' +
                ", sessionToken='" + sessionToken + '\'' +
                '}';
    }
}
