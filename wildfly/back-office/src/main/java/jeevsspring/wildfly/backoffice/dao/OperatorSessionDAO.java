package jeevsspring.wildfly.backoffice.dao;

import jeevsspring.wildfly.backoffice.entity.OperatorSessionEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author Marco Romagnolo
 */
@ApplicationScoped
public class OperatorSessionDAO {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    public OperatorSessionEntity getByIdAndToken(String id, String token) {
        Query query = em.createQuery("FROM OperatorSessionEntity WHERE id = :id AND token = :token");
        query.setParameter("id", id);
        query.setParameter("token", token);
        return (OperatorSessionEntity) query.getSingleResult();
    }

    public void save(OperatorSessionEntity session) {
        em.persist(session);
    }

    public void delete(String sessionId) {
        OperatorSessionEntity session = em.find(OperatorSessionEntity.class, sessionId);
        em.remove(session);
    }
}
