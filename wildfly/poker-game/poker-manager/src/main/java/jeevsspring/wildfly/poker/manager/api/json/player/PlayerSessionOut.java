package jeevsspring.wildfly.poker.manager.api.json.player;

import jeevsspring.wildfly.poker.manager.api.json.Status;

public class PlayerSessionOut extends Status {

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
