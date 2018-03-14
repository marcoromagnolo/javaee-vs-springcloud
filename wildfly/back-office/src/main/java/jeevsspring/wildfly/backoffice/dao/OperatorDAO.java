package jeevsspring.wildfly.backoffice.dao;

import jeevsspring.wildfly.backoffice.entity.OperatorEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author Marco Romagnolo
 */
@ApplicationScoped
public class OperatorDAO {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    public OperatorEntity getByUsernameAndPassword(String username, String password) {
        Query query = em.createQuery("FROM OperatorEntity WHERE username = :username AND password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        return (OperatorEntity) query.getSingleResult();
    }
}
