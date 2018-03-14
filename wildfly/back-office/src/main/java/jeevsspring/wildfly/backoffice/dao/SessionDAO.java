package jeevsspring.wildfly.backoffice.dao;

import jeevsspring.wildfly.backoffice.entity.OperatorSessionEntity;
import jeevsspring.wildfly.backoffice.entity.SessionEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author Marco Romagnolo
 */
@ApplicationScoped
public class SessionDAO {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    public SessionEntity getByIdAndToken(String id, String token) {
        Query query = em.createQuery("FROM SessionEntity WHERE id = :id AND token = :token");
        query.setParameter("id", id);
        query.setParameter("token", token);
        return (SessionEntity) query.getSingleResult();
    }

    public void save(SessionEntity session) {
        em.persist(session);
    }

    public void delete(String id) {
        OperatorSessionEntity session = em.find(OperatorSessionEntity.class, id);
        em.remove(session);
    }
}
