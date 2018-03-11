package jeevsspring.wildfly.poker.manager.bo.json;

/**
 * @author Marco Romagnolo
 */
public class BOLoginOut extends BOSessionOut {

    private String sessionId;
    private String token;
    private BOUserOut user;

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

    public BOUserOut getUser() {
        return user;
    }

    public void setUser(BOUserOut user) {
        this.user = user;
    }
}
