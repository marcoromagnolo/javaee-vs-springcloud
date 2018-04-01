package jeevsspring.spring.backoffice.player.dao;

import jeevsspring.spring.backoffice.player.entity.WalletEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Marco Romagnolo
 */
public interface WalletDAO extends CrudRepository<WalletEntity, Long> {

    @Query("SELECT w FROM WalletEntity w WHERE w.player.id = :playerId")
    WalletEntity findByPlayerId(String playerId);

    WalletEntity save(WalletEntity entity);
}
