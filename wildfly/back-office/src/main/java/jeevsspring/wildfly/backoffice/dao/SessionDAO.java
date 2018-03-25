package jeevsspring.wildfly.backoffice.dao;

import jeevsspring.wildfly.backoffice.entity.OperatorSessionEntity;
import jeevsspring.wildfly.backoffice.entity.SessionEntity;
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
public class SessionDAO {

    //JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    public SessionEntity getByIdAndToken(String id, String token) {
        Query query = em.createQuery("SELECT s FROM SessionEntity s WHERE s.id = :id AND s.token = :token");
        query.setParameter("id", id);
        query.setParameter("token", token);
        List list = query.getResultList();
        SessionEntity entity;
        if (list.isEmpty()) entity = null;
        else entity= (SessionEntity) list.get(0);
        logger.debug("getByIdAndToken(" + id + ", " + token + ") return " + entity);
        return entity;
    }

    public void insert(SessionEntity entity) {
        em.persist(entity);
        logger.debug("insert(" + entity + ")");
    }

    public void delete(String id) {
        OperatorSessionEntity entity = em.find(OperatorSessionEntity.class, id);
        em.remove(entity);
        logger.debug("delete(" + entity + ")");
    }
}
