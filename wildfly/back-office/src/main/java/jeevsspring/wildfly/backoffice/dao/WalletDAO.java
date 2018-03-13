package jeevsspring.wildfly.backoffice.dao;

import jeevsspring.wildfly.backoffice.entity.WalletEntity;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Marco Romagnolo
 */
@Singleton
public class WalletDAO {

    @PersistenceContext
    EntityManager em;

    public WalletEntity getByPlayerId(String playerId) {
        return null;
    }

    public WalletEntity sub(String playerId, long amount) {
        return null;
    }

    public WalletEntity add(String playerId, long amount) {
        return null;
    }
}
