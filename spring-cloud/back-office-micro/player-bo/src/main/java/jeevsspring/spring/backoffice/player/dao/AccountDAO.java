package jeevsspring.spring.backoffice.player.dao;

import jeevsspring.spring.backoffice.player.entity.AccountEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Marco Romagnolo
 */
public interface AccountDAO extends CrudRepository<AccountEntity, Long> {

    @Query("SELECT a FROM AccountEntity a WHERE a.player.id = :playerId")
    AccountEntity findByPlayerId(String playerId);
}
