package jeevsspring.wildfly.backoffice.dao;

import jeevsspring.wildfly.backoffice.entity.AccountEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author Marco Romagnolo
 */
@ApplicationScoped
public class AccountDAO {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    public AccountEntity getByPlayerId(String playerId) {
        Query query = em.createQuery("FROM AccountEntity WHERE player.id = :playerId");
        query.setParameter("playerId", playerId);
        return (AccountEntity) query.getSingleResult();
    }
}
