package jeevsspring.wildfly.backoffice.dao;

import jeevsspring.wildfly.backoffice.entity.OperatorSessionEntity;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author Marco Romagnolo
 */
@ApplicationScoped
public class OperatorSessionDAO {

    //JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    public OperatorSessionEntity getByIdAndToken(String id, String token) {
        Query query = em.createQuery("FROM OperatorSessionEntity WHERE id = :id AND token = :token");
        query.setParameter("id", id);
        query.setParameter("token", token);
        OperatorSessionEntity entity = (OperatorSessionEntity) query.getSingleResult();
        logger.debug("getByIdAndToken(" + id + ", " + token + ") return " + entity);
        return entity;
    }

    public void insert(OperatorSessionEntity entity) {
        em.persist(entity);
        logger.debug("insert(" + entity + ")");
    }

    public void delete(String sessionId) {
        OperatorSessionEntity entity = em.find(OperatorSessionEntity.class, sessionId);
        em.remove(entity);
        logger.debug("delete(" + entity + ")");
    }
}
