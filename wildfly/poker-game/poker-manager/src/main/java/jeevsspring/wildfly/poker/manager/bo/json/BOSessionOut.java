package jeevsspring.wildfly.poker.manager.bo.json;

/**
 * @author Marco Romagnolo
 */
public class BOSessionOut extends BOStatus {

    private String sessionId;

    private String sessionToken;

    private long sessionExpireTime;

    private long sessionCreateTime;

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

    public long getSessionExpireTime() {
        return sessionExpireTime;
    }

    public void setSessionExpireTime(long sessionExpireTime) {
        this.sessionExpireTime = sessionExpireTime;
    }

    public long getSessionCreateTime() {
        return sessionCreateTime;
    }

    public void setSessionCreateTime(long sessionCreateTime) {
        this.sessionCreateTime = sessionCreateTime;
    }

    @Override
    public String toString() {
        return "BOSessionOut{" +
                "sessionId='" + sessionId + '\'' +
                ", sessionToken='" + sessionToken + '\'' +
                ", sessionExpireTime=" + sessionExpireTime +
                ", sessionCreateTime=" + sessionCreateTime +
                "} " + super.toString();
    }
}
