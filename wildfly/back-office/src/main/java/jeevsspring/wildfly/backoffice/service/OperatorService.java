package jeevsspring.wildfly.backoffice.service;

import jeevsspring.wildfly.backoffice.api.json.OperatorLoginOut;
import jeevsspring.wildfly.backoffice.api.json.OperatorLogoutOut;
import jeevsspring.wildfly.backoffice.dao.OperatorDAO;
import jeevsspring.wildfly.backoffice.dao.OperatorSessionDAO;
import jeevsspring.wildfly.backoffice.entity.OperatorEntity;
import jeevsspring.wildfly.backoffice.entity.OperatorSessionEntity;
import jeevsspring.wildfly.backoffice.util.BOConfig;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.UUID;

/**
 * @author Marco Romagnolo
 */
@ApplicationScoped
@Transactional
public class OperatorService {

    @Inject
    private OperatorDAO operatorDAO;

    @Inject
    private OperatorSessionDAO sessionDAO;

    @Inject
    private BOConfig config;

    /**
     * Operator Login Service
     * @param username
     * @param password
     * @return
     * @throws ServiceException
     */
    public OperatorLoginOut login(String username, String password) throws ServiceException, AuthenticationException {
        try {
            OperatorEntity operator = operatorDAO.getByUsernameAndPassword(username, password);

            if (operator == null) throw new AuthenticationException();
            OperatorSessionEntity session = new OperatorSessionEntity();
            session.setId(UUID.randomUUID().toString());
            session.setToken(UUID.randomUUID().toString());
            session.setOperator(operator);
            long now = System.currentTimeMillis();
            session.setCreateTime(now);
            int duration = config.getPlayerSessionDuration() * 1000;
            session.setExpireTime(now + duration);
            sessionDAO.insert(session);

            OperatorLoginOut out = new OperatorLoginOut();
            out.setOperatorId(operator.getId().toString());
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
            throw new ServiceException(ErrorType.DATABASE_ERROR);
        }
    }

    /**
     * Operator Logout Service
     * @param operatorId
     * @param sessionId
     * @param sessionToken
     * @return
     * @throws ServiceException
     */
    public OperatorLogoutOut logout(String operatorId, String sessionId, String sessionToken) throws ServiceException {
        sessionCheck(operatorId, sessionId, sessionToken);
        try {
            sessionDAO.delete(sessionId);
            OperatorLogoutOut out = new OperatorLogoutOut();
            out.setOperatorId(operatorId);
            out.setMessage("Logged Out");
            return out;
        } catch (PersistenceException e) {
            throw new ServiceException(ErrorType.DATABASE_ERROR);
        }
    }

    /**
     * Operator Session Check
     * @param operatorId
     * @param sessionId
     * @param sessionToken
     * @throws ServiceException
     */
    private void sessionCheck(String operatorId, String sessionId, String sessionToken) throws ServiceException {
        try {
            OperatorSessionEntity entity = sessionDAO.getByIdAndToken(sessionId, sessionToken);
            if (!entity.getOperator().getId().equals(operatorId)) {
                throw new ServiceException(ErrorType.AUTH_ERROR);
            }
        } catch (PersistenceException e) {
            throw new ServiceException(ErrorType.DATABASE_ERROR);
        }
    }

}
