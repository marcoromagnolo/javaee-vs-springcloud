package jeevsspring.wildfly.backoffice.service;

import jeevsspring.wildfly.backoffice.api.json.*;
import jeevsspring.wildfly.backoffice.dao.OperatorDAO;
import jeevsspring.wildfly.backoffice.dao.SessionDAO;
import jeevsspring.wildfly.backoffice.entity.OperatorEntity;
import jeevsspring.wildfly.backoffice.entity.PlayerEntity;
import jeevsspring.wildfly.backoffice.entity.SessionEntity;
import jeevsspring.wildfly.backoffice.entity.WalletEntity;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.PersistenceException;

/**
 * @author Marco Romagnolo
 */
@Singleton
public class OperatorService {

    @Inject
    private OperatorDAO operatorDAO;

    @Inject
    private SessionDAO sessionDAO;

    public OperatorLoginOut login(String username, String password) throws ServiceException {
        try {
            OperatorEntity entity = operatorDAO.getByUsernameAndPassword(username, password);
            OperatorLoginOut out = new OperatorLoginOut();

            out.setOperatorId(entity.getId());
            out.setUsername(entity.getUsername());
            // Only for refresh session
//            out.setSessionId(entity.getSessionId());
//            out.setSessionToken(entity.getSessionToken());
//            out.setSessionCreateTime(entity.getSessionCreateTime());
//            out.setSessionExpireTime(entity.getSessionCreateTime());

            // Set Account
            out.setFirstName(entity.getFirstName());
            out.setLastName(entity.getLastName());

            return out;
        } catch (PersistenceException e) {
            throw new ServiceException("Database Error", ErrorType.DATABASE_ERROR);
        }
    }

    public OperatorLogoutOut logout(String operatorId, String sessionId, String sessionToken) throws ServiceException {
        sessionCheck(operatorId, sessionId, sessionToken);
        try {
            OperatorLogoutOut out = new OperatorLogoutOut();
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
