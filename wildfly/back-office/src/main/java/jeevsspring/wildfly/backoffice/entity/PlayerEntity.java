package jeevsspring.wildfly.backoffice.entity;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

/**
 * @author Marco Romagnolo
 */
@Entity
@Cacheable
public class PlayerEntity {

    private String playerId;

    private String nickname;

    private String username;

    private String password;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
