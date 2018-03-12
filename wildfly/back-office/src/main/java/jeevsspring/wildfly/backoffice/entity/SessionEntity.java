package jeevsspring.wildfly.backoffice.entity;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

/**
 * @author Marco Romagnolo
 */
@Entity
@Cacheable
public class SessionEntity {

    private String id;

    private String playerId;

    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
