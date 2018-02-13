package jeevsspring.wildfly.poker.manager.bo.json;

/**
 * @author Marco Romagnolo
 */
public class SigninOut extends SessionOut {

    private String session;
    private String token;
    private UserOut user;

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

    public UserOut getUser() {
        return user;
    }

    public void setUser(UserOut user) {
        this.user = user;
    }
}
