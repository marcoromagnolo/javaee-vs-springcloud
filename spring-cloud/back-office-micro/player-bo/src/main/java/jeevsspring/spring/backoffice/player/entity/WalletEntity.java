package jeevsspring.spring.backoffice.player.entity;

import org.springframework.cache.annotation.Cacheable;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Marco Romagnolo
 */
@Entity
@Table(name="WALLET")
public class WalletEntity implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="player_id")
    private PlayerEntity player;

    @Column(name = "balance")
    private long balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerEntity player) {
        this.player = player;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}
