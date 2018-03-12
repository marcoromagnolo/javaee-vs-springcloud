package jeevsspring.wildfly.backoffice.service;

import jeevsspring.wildfly.backoffice.api.json.OperatorLoginOut;
import jeevsspring.wildfly.backoffice.api.json.OperatorLogoutOut;
import jeevsspring.wildfly.backoffice.dao.OperatorDAO;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Marco Romagnolo
 */
@Singleton
public class OperatorService {

    @Inject
    private OperatorDAO operatorDAO;

    public OperatorLoginOut login(String username, String password) throws ServiceException {
        out.setSessionId(bo.getSessionId());
        out.setSessionToken(bo.getSessionToken());
        out.setNickname(bo.getNickname());
        out.setSessionCreateTime(bo.getSessionCreateTime());
        out.setSessionExpireTime(bo.getSessionCreateTime());
    }

    public OperatorLogoutOut logout(String operatorId, String sessionId, String sessionToken) throws ServiceException {

    }
}
