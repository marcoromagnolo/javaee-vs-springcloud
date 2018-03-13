package jeevsspring.wildfly.backoffice.service;

import jeevsspring.wildfly.backoffice.api.json.*;
import jeevsspring.wildfly.backoffice.dao.AccountDAO;
import jeevsspring.wildfly.backoffice.dao.PlayerDAO;
import jeevsspring.wildfly.backoffice.dao.SessionDAO;
import jeevsspring.wildfly.backoffice.dao.WalletDAO;
import jeevsspring.wildfly.backoffice.entity.AccountEntity;
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
public class PlayerService {

    @Inject
    private WalletDAO walletDAO;

    @Inject
    private PlayerDAO playerDAO;

    @Inject
    private AccountDAO accountDAO;

    @Inject
    private SessionDAO sessionDAO;

    public WalletOut wallet(String playerId, String sessionId, String sessionToken) throws ServiceException {
        sessionCheck(playerId, sessionId, sessionToken);
        try {
            WalletEntity entity = walletDAO.getByPlayerId(playerId);
            WalletOut out = new WalletOut();
            out.setBalance(entity.getBalance());
            return out;
        } catch (PersistenceException e) {
            throw new ServiceException("Database Error", ErrorType.DATABASE_ERROR);
        }
    }

    public AccountOut account(String playerId, String sessionId, String sessionToken) throws ServiceException {
        sessionCheck(playerId, sessionId, sessionToken);
        try {
            AccountEntity entity = accountDAO.getByPlayerId(playerId);
            AccountOut out = new AccountOut();
            out.setFirstName(entity.getFirstName());
            out.setLastName(entity.getLastName());
            return out;
        } catch (PersistenceException e) {
            throw new ServiceException("Database Error", ErrorType.DATABASE_ERROR);
        }
    }

    public LoginOut login(String username, String password) throws ServiceException {
        try {
            PlayerEntity entity = playerDAO.getByUsernameAndPassword(username, password);
            LoginOut out = new LoginOut();

            out.setPlayerId(entity.getId());
            out.setNickname(entity.getNickname());
            // Only for refresh session
//            out.setSessionId(entity.getSessionId());
//            out.setSessionToken(entity.getSessionToken());
//            out.setSessionCreateTime(entity.getSessionCreateTime());
//            out.setSessionExpireTime(entity.getSessionCreateTime());

            // Set Account
            out.setFirstName(entity.getAccount().getFirstName());
            out.setLastName(entity.getAccount().getLastName());

            // Set Wallet
            out.setBalance(entity.getWallet().getBalance());
            return out;
        } catch (PersistenceException e) {
            throw new ServiceException("Database Error", ErrorType.DATABASE_ERROR);
        }
    }

    public LogoutOut logout(String playerId, String sessionId, String sessionToken) throws ServiceException {
        sessionCheck(playerId, sessionId, sessionToken);
        try {
            LogoutOut out = new LogoutOut();
            return out;
        } catch (PersistenceException e) {
            throw new ServiceException("Database Error", ErrorType.DATABASE_ERROR);
        }
    }

    public StakeOut stake(String playerId, String sessionId, String sessionToken, long amount) throws ServiceException {
        sessionCheck(playerId, sessionId, sessionToken);
        try {
            WalletEntity entity = walletDAO.sub(playerId, amount);
            StakeOut out = new StakeOut();
            out.setBalance(entity.getBalance());
            return out;
        } catch (PersistenceException e) {
            throw new ServiceException("Database Error", ErrorType.DATABASE_ERROR);
        }
    }

    public WinOut win(String playerId, String sessionId, String sessionToken, long amount) throws ServiceException {
        sessionCheck(playerId, sessionId, sessionToken);
        try {
            WalletEntity entity = walletDAO.add(playerId, amount);
            WinOut out = new WinOut();
            out.setBalance(entity.getBalance());
            return out;
        } catch (PersistenceException e) {
            throw new ServiceException("Database Error", ErrorType.DATABASE_ERROR);
        }
    }

    public RefundOut refund(String playerId, String sessionId, String sessionToken, long amount) throws ServiceException {
        sessionCheck(playerId, sessionId, sessionToken);
        try {
            WalletEntity entity = walletDAO.add(playerId, amount);
            RefundOut out = new RefundOut();
            out.setBalance(entity.getBalance());
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
