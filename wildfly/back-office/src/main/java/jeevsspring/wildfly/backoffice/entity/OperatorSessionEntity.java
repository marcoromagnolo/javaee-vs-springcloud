package jeevsspring.wildfly.backoffice.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Marco Romagnolo
 */
@Entity
@Cacheable
@Table(name="OPERATOR_SESSION")
public class OperatorSessionEntity implements Serializable {

    @Id
    private String id;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="operator_id")
    private OperatorEntity operator;

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

    public OperatorEntity getOperator() {
        return operator;
    }

    public void setOperator(OperatorEntity playerId) {
        this.operator = playerId;
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
