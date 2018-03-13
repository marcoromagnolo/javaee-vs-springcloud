package jeevsspring.wildfly.backoffice.dao;

import jeevsspring.wildfly.backoffice.entity.OperatorEntity;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Marco Romagnolo
 */
@Singleton
public class OperatorDAO {

    @PersistenceContext
    EntityManager em;

    public OperatorEntity getByUsernameAndPassword(String username, String password) {
        return null;
    }

    public void delete(String sessionId) {

    }
}
