package jeevsspring.wildfly.backoffice.dao;

import jeevsspring.wildfly.backoffice.entity.AccountEntity;
import jeevsspring.wildfly.backoffice.entity.OperatorSessionEntity;
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
public class OperatorSessionDAO {

    //JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    public OperatorSessionEntity getByIdAndToken(String id, String token) {
        Query query = em.createQuery("SELECT os FROM OperatorSessionEntity os WHERE os.id = :id AND os.token = :token");
        query.setParameter("id", id);
        query.setParameter("token", token);
        List list = query.getResultList();
        OperatorSessionEntity entity;
        if (list.isEmpty()) entity = null;
        else entity= (OperatorSessionEntity) list.get(0);
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
