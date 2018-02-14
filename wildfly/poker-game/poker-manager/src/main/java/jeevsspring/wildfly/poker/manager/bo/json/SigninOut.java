package jeevsspring.wildfly.poker.manager.bo.json;

/**
 * @author Marco Romagnolo
 */
public class SigninOut extends SessionOut {

    private String sessionId;
    private String token;
    private UserOut user;

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

    public UserOut getUser() {
        return user;
    }

    public void setUser(UserOut user) {
        this.user = user;
    }
}
