package jeevsspring.spring.util;

import java.io.Serializable;

/**
 * @author Marco Romagnolo
 */
public class OperatorSession implements Serializable {

    private String id;
    private String username;
    private String sessionId;
    private String sessionToken;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "OperatorSession{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", sessionToken='" + sessionToken + '\'' +
                '}';
    }
}
