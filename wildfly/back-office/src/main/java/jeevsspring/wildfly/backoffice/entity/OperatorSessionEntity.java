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
    @JoinColumn(name="OPERATOR_ID")
    private OperatorEntity operator;

    @Column("TOKEN")
    private String token;

    @Column("CREATE_TIME")
    private long createTime;

    @Column("EXPIRE_TIME")
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
