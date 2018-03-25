package jeevsspring.wildfly.backoffice.dao;

import jeevsspring.wildfly.backoffice.entity.SessionEntity;
import jeevsspring.wildfly.backoffice.entity.WalletEntity;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author Marco Romagnolo
 */
@ApplicationScoped
public class WalletDAO {

    //JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    public WalletEntity getByPlayerId(String playerId) {
        Query query = em.createQuery("SELECT w FROM WalletEntity w WHERE w.player.id = :playerId");
        query.setParameter("playerId", playerId);
        List list = query.getResultList();
        WalletEntity entity;
        if (list.isEmpty()) entity = null;
        else entity= (WalletEntity) list.get(0);
        logger.debug("getByPlayerId(" + playerId + ") return " + entity);
        return entity;
    }

    public void insert(WalletEntity entity) {
        em.persist(entity);
        logger.debug("insert(" + entity + ")");
    }
}
