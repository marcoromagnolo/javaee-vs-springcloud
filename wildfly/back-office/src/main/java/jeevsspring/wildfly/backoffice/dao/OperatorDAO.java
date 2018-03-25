package jeevsspring.wildfly.backoffice.dao;

import jeevsspring.wildfly.backoffice.entity.OperatorEntity;
import jeevsspring.wildfly.backoffice.entity.PlayerEntity;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author Marco Romagnolo
 */
@ApplicationScoped
public class OperatorDAO {

    //JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @PersistenceContext(unitName = "primary")
    private EntityManager em;

    public OperatorEntity getByUsernameAndPassword(String username, String password) {
        Query query = em.createQuery("SELECT o FROM OperatorEntity o WHERE o.username = :username AND o.password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        List list = query.getResultList();
        OperatorEntity entity;
        if (list.isEmpty()) entity = null;
        else entity= (OperatorEntity) list.get(0);
        logger.debug("getByUsernameAndPassword(" + username + ", " + password + ") return " + entity);
        return entity;
    }
}
