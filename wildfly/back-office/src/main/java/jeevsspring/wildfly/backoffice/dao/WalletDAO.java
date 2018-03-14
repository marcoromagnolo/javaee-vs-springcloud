package jeevsspring.wildfly.backoffice.dao;

import jeevsspring.wildfly.backoffice.entity.WalletEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author Marco Romagnolo
 */
@ApplicationScoped
public class WalletDAO {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    public WalletEntity getByPlayerId(String playerId) {
        Query query = em.createQuery("FROM WalletEntity WHERE player.id = :playerId");
        query.setParameter("playerId", playerId);
        return (WalletEntity) query.getSingleResult();
    }

    public void save(WalletEntity entity) {
        em.persist(entity);
    }
}
