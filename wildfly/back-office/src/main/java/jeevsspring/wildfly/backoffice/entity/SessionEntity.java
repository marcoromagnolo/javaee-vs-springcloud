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
    @JoinColumn(name="player_id")
    private PlayerEntity player;

    @Column(name = "token")
    private String token;

    @Column(name = "create_time")
    private long createTime;

    @Column(name = "expire_time")
    private long expireTime;

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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
