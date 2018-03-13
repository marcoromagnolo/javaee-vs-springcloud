package jeevsspring.wildfly.backoffice.dao;

import jeevsspring.wildfly.backoffice.entity.SessionEntity;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Marco Romagnolo
 */
@Singleton
public class SessionDAO {

    @PersistenceContext
    EntityManager em;

    public SessionEntity getByPlayerId(String playerId) {
        return null;
    }

    public SessionEntity getByIdAndToken(String sessionId, String sessionToken) {
        return null;
    }
}
