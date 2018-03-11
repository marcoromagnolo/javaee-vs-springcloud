package jeevsspring.wildfly.poker.manager.api.json.player;

/**
 * @author Marco Romagnolo
 */
public class PlayerSessionIn {

    private String sessionId;
    private String token;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
