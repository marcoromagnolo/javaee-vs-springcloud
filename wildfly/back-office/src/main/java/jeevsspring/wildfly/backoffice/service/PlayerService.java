package jeevsspring.wildfly.backoffice.service;

import jeevsspring.wildfly.backoffice.api.json.*;
import jeevsspring.wildfly.backoffice.dao.AccountDAO;
import jeevsspring.wildfly.backoffice.dao.PlayerDAO;
import jeevsspring.wildfly.backoffice.dao.SessionDAO;
import jeevsspring.wildfly.backoffice.dao.WalletDAO;
import jeevsspring.wildfly.backoffice.entity.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.PersistenceException;
import java.util.UUID;

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

    public LoginOut login(String username, String password) throws ServiceException {
        try {
            PlayerEntity player = playerDAO.getByUsernameAndPassword(username, password);

            SessionEntity session = new SessionEntity();
            session.setId(UUID.randomUUID().toString());
            session.setToken(UUID.randomUUID().toString());
            session.setPlayer(player);
            long now = System.currentTimeMillis();
            session.setCreateTime(now);
            session.setExpireTime(now + 3600_000);
            sessionDAO.save(session);
            LoginOut out = new LoginOut();

            out.setPlayerId(player.getId());
            out.setNickname(player.getNickname());
            out.setSessionId(session.getId());
            out.setSessionToken(session.getToken());
            out.setSessionCreateTime(session.getCreateTime());
            out.setSessionExpireTime(session.getCreateTime());

            // Set Account
            out.setFirstName(player.getAccount().getFirstName());
            out.setLastName(player.getAccount().getLastName());

            // Set Wallet
            out.setBalance(player.getWallet().getBalance());
            return out;
        } catch (PersistenceException e) {
            throw new ServiceException("Database Error", ErrorType.DATABASE_ERROR);
        }
    }

    public LogoutOut logout(String playerId, String sessionId, String sessionToken) throws ServiceException {
        sessionCheck(playerId, sessionId, sessionToken);
        try {
            sessionDAO.delete(sessionId);
            LogoutOut out = new LogoutOut();
            out.setPlayerId(playerId);
            out.setMessage("Logged Out");
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

    public WalletOut wallet(String playerId, String sessionId, String sessionToken) throws ServiceException {
        sessionCheck(playerId, sessionId, sessionToken);
        try {
            WalletEntity entity = walletDAO.getByPlayerId(playerId);
            WalletOut out = new WalletOut();
            out.setPlayerId(playerId);
            out.setBalance(entity.getBalance());
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
            out.setPlayerId(playerId);
            out.setNickname(entity.getPlayer().getNickname());
            out.setBalance(entity.getBalance());
            out.setAmount(amount);
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
            out.setPlayerId(playerId);
            out.setNickname(entity.getPlayer().getNickname());
            out.setBalance(entity.getBalance());
            out.setAmount(amount);
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
            out.setPlayerId(playerId);
            out.setNickname(entity.getPlayer().getNickname());
            out.setBalance(entity.getBalance());
            out.setAmount(amount);
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
