package jeevsspring.wildfly.backoffice.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Marco Romagnolo
 */
@Entity
@Cacheable
@Table(name="SESSION")
public class SessionEntity implements Serializable {

    @Id
    private String id;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PLAYER_ID")
    private PlayerEntity player;

    @Column("TOKEN")
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerEntity playerId) {
        this.player = playerId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
