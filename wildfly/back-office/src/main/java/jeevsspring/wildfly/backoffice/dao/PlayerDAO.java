package jeevsspring.wildfly.backoffice.dao;

import jeevsspring.wildfly.backoffice.entity.PlayerEntity;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Marco Romagnolo
 */
@Singleton
public class PlayerDAO {

    @PersistenceContext
    EntityManager em;

    public PlayerEntity getByUsernameAndPassword(String username, String password) {
        return null;
    }

}
