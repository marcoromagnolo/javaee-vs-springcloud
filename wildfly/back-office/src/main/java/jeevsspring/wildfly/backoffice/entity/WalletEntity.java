package jeevsspring.wildfly.backoffice.entity;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

/**
 * @author Marco Romagnolo
 */
@Entity
@Cacheable
public class WalletEntity {

    private String playerId;

    private long balance;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}
