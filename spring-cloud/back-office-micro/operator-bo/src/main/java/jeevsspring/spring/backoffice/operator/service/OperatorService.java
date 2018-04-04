package jeevsspring.spring.backoffice.operator.service;

import jeevsspring.spring.backoffice.operator.api.json.OperatorLoginOut;
import jeevsspring.spring.backoffice.operator.api.json.OperatorLogoutOut;
import jeevsspring.spring.backoffice.operator.dao.OperatorDAO;
import jeevsspring.spring.backoffice.operator.dao.OperatorSessionDAO;
import jeevsspring.spring.backoffice.operator.entity.OperatorEntity;
import jeevsspring.spring.backoffice.operator.entity.OperatorSessionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marco Romagnolo
 */
@Service
@Transactional
public class OperatorService {

    private final Logger logger = Logger.getLogger(getClass().toString());

    @Autowired
    private OperatorDAO operatorDAO;

    @Autowired
    private OperatorSessionDAO sessionDAO;

    @Autowired
    private Environment env;

    /**
     * Operator Login Service
     * @param username
     * @param password
     * @return
     */
    public OperatorLoginOut login(String username, String password) throws AuthenticationException {
        logger.log(Level.FINE, "login(" + username + ", " + password + ")");

        // Calculate Hash
        String hash = HashPassword.hash(password);

        OperatorEntity operator = operatorDAO.findByUsernameAndPassword(username, hash);

        if (operator == null) throw new AuthenticationException();

        OperatorSessionEntity session = new OperatorSessionEntity();
        session.setId(UUID.randomUUID().toString());
        session.setToken(UUID.randomUUID().toString());
        session.setOperator(operator);
        long now = System.currentTimeMillis();
        session.setCreateTime(now);
        int duration = Integer.parseInt(env.getProperty("player.session.duration")) * 1000;
        session.setExpireTime(now + duration);
        sessionDAO.save(session);

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
    }

    /**
     * Operator Logout Service
     * @param operatorId
     * @param sessionId
     * @param sessionToken
     * @return
     */
    public OperatorLogoutOut logout(String operatorId, String sessionId, String sessionToken) throws InvalidSessionException {
        sessionCheck(operatorId, sessionId, sessionToken);

        sessionDAO.delete(sessionId);
        OperatorLogoutOut out = new OperatorLogoutOut();
        out.setOperatorId(operatorId);
        out.setMessage("Logged Out");
        return out;
    }

    /**
     * Operator Session Check
     * @param operatorId
     * @param sessionId
     * @param sessionToken
     */
    private void sessionCheck(String operatorId, String sessionId, String sessionToken) throws InvalidSessionException {
        OperatorSessionEntity entity = sessionDAO.findByIdAndToken(sessionId, sessionToken);
        if (entity == null || !entity.getOperator().getId().equals(operatorId)) {
            throw new InvalidSessionException();
        }
    }

}
