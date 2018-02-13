package jeevsspring.wildfly.poker.manager.api.json.lobby;

public class PlayerSessionOut extends ApiStatus {

    private String session;
    private String token;

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
