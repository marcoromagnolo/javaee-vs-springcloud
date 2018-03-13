package jeevsspring.wildfly.backoffice.dao;

import jeevsspring.wildfly.backoffice.entity.OperatorSessionEntity;
import jeevsspring.wildfly.backoffice.entity.SessionEntity;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Marco Romagnolo
 */
@Singleton
public class OperatorSessionDAO {

    @PersistenceContext
    EntityManager em;

    public SessionEntity getByOperatorId(String operatorId) {
        return null;
    }

    public SessionEntity getByIdAndToken(String sessionId, String sessionToken) {
        return null;
    }

    public void save(OperatorSessionEntity session) {

    }
}
