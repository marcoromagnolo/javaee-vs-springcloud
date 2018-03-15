package jeevsspring.wildfly.backoffice.dao;

import jeevsspring.wildfly.backoffice.entity.PlayerEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * @author Marco Romagnolo
 */
@ApplicationScoped
public class PlayerDAO {

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    public PlayerEntity getByUsernameAndPassword(String username, String password) {
        Query query = em.createQuery("FROM PlayerEntity WHERE username = :username AND password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        return (PlayerEntity) query.getSingleResult();
    }

    public PlayerEntity get(String id) {
        return em.find(PlayerEntity.class, id);
    }

    public void insert(PlayerEntity player) {
        em.persist(player);
    }
}
