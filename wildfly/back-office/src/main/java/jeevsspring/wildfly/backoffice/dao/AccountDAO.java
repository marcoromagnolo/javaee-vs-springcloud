package jeevsspring.wildfly.backoffice.dao;

import jeevsspring.wildfly.backoffice.entity.AccountEntity;
import jeevsspring.wildfly.backoffice.entity.OperatorEntity;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

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
        Query query = em.createQuery("SELECT a FROM AccountEntity a WHERE a.player.id = :playerId");
        query.setParameter("playerId", playerId);
        List list = query.getResultList();
        AccountEntity entity;
        if (list.isEmpty()) entity = null;
        else entity= (AccountEntity) list.get(0);
        logger.debug("getByPlayerId(" + playerId + ") return " + entity);
        return entity;
    }
}
