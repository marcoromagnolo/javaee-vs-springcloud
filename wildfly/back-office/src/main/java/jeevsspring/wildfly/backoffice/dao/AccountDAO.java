package jeevsspring.wildfly.backoffice.dao;

import jeevsspring.wildfly.backoffice.entity.AccountEntity;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author Marco Romagnolo
 */
@ApplicationScoped
public class AccountDAO {

    //JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    public AccountEntity getByPlayerId(String playerId) {
        Query query = em.createQuery("FROM AccountEntity WHERE player.id = :playerId");
        query.setParameter("playerId", playerId);
        AccountEntity entity = (AccountEntity) query.getSingleResult();
        logger.debug("getByPlayerId(" + playerId + ") return " + entity);
        return entity;
    }
}
