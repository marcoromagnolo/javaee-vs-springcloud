package jeevsspring.wildfly.backoffice.dao;

import jeevsspring.wildfly.backoffice.entity.PlayerEntity;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.List;

/**
 * @author Marco Romagnolo
 */
@RequestScoped
@Transactional(value = Transactional.TxType.REQUIRES_NEW,
        rollbackOn = {SQLException.class},
        dontRollbackOn = {SQLWarning.class})
public class PlayerDAO {

    //JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    public PlayerEntity getByUsernameAndPassword(String username, String password) {
        logger.debug("getByUsernameAndPassword(" + username + ", " + password + ")");
        Query query = em.createQuery("SELECT p FROM PlayerEntity p WHERE p.username = :username AND p.password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        List list = query.getResultList();
        PlayerEntity entity;
        if (list.isEmpty()) entity = null;
        else entity= (PlayerEntity) list.get(0);
        logger.debug("getByUsernameAndPassword(" + username + ", " + password + ") return " + entity);
        return entity;
    }

    public PlayerEntity get(String id) {
        PlayerEntity entity = em.find(PlayerEntity.class, id);
        logger.debug("get(" + id + ") return " + entity);
        return entity;
    }

    public void insert(PlayerEntity entity) {
        em.persist(entity);
        logger.debug("insert(" + entity + ")");
    }
}
