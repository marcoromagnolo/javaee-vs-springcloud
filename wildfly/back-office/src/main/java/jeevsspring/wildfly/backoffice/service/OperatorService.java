package jeevsspring.wildfly.backoffice.service;

import jeevsspring.wildfly.backoffice.api.json.OperatorLoginOut;
import jeevsspring.wildfly.backoffice.api.json.OperatorLogoutOut;
import jeevsspring.wildfly.backoffice.dao.OperatorDAO;
import jeevsspring.wildfly.backoffice.dao.OperatorSessionDAO;
import jeevsspring.wildfly.backoffice.dao.SessionDAO;
import jeevsspring.wildfly.backoffice.entity.OperatorEntity;
import jeevsspring.wildfly.backoffice.entity.OperatorSessionEntity;
import jeevsspring.wildfly.backoffice.entity.SessionEntity;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.PersistenceException;
import java.util.Date;
import java.util.UUID;

/**
 * @author Marco Romagnolo
 */
@Singleton
public class OperatorService {

    @Inject
    private OperatorDAO operatorDAO;

    @Inject
    private OperatorSessionDAO sessionDAO;

    public OperatorLoginOut login(String username, String password) throws ServiceException {
        try {
            OperatorEntity operator = operatorDAO.getByUsernameAndPassword(username, password);

            OperatorSessionEntity session = new OperatorSessionEntity();
            session.setId(UUID.randomUUID().toString());
            session.setToken(UUID.randomUUID().toString());
            session.setOperator(operator);
            long now = System.currentTimeMillis();
            session.setCreateTime(now);
            session.setExpireTime(now + 3600_000);
            sessionDAO.save(session);

            OperatorLoginOut out = new OperatorLoginOut();
            out.setOperatorId(operator.getId());
            out.setUsername(operator.getUsername());
            out.setSessionId(session.getId());
            out.setSessionToken(session.getToken());
            out.setSessionCreateTime(session.getCreateTime());
            out.setSessionExpireTime(session.getCreateTime());

            // Set Account
            out.setFirstName(operator.getFirstName());
            out.setLastName(operator.getLastName());
            out.setEmail(operator.getEmail());

            return out;
        } catch (PersistenceException e) {
            throw new ServiceException("Database Error", ErrorType.DATABASE_ERROR);
        }
    }

    public OperatorLogoutOut logout(String operatorId, String sessionId, String sessionToken) throws ServiceException {
        sessionCheck(operatorId, sessionId, sessionToken);
        try {
            operatorDAO.delete(sessionId);
            OperatorLogoutOut out = new OperatorLogoutOut();
            out.setOperatorId(operatorId);
            out.setMessage("Logged Out");
            return out;
        } catch (PersistenceException e) {
            throw new ServiceException("Database Error", ErrorType.DATABASE_ERROR);
        }
    }

    private void sessionCheck(String playerId, String sessionId, String sessionToken) throws ServiceException {
        try {
            SessionEntity entity = sessionDAO.getByIdAndToken(sessionId, sessionToken);
        } catch (PersistenceException e) {
            throw new ServiceException("Database Error", ErrorType.DATABASE_ERROR);
        }
    }

}
