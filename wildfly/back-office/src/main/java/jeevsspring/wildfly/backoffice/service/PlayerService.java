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
import jeevsspring.wildfly.backoffice.util.BOConfig;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * @author Marco Romagnolo
 */
@ApplicationScoped
@Transactional
public class PlayerService {

    @Inject
    private WalletDAO walletDAO;

    @Inject
    private PlayerDAO playerDAO;

    @Inject
    private AccountDAO accountDAO;

    @Inject
    private SessionDAO sessionDAO;

    @Inject
    private BOConfig config;

    /**
     * Player Login Service
     * @param username
     * @param password
     * @return
     * @throws ServiceException
     */
    public LoginOut login(String username, String password) throws ServiceException {
        try {
            // MD5 Hash of Password
            String hash = MessageDigest.getInstance("MD5").toString();
            PlayerEntity player = playerDAO.getByUsernameAndPassword(username, hash);

            SessionEntity session = new SessionEntity();
            session.setId(UUID.randomUUID().toString());
            session.setToken(UUID.randomUUID().toString());
            session.setPlayer(player);
            long now = System.currentTimeMillis();
            session.setCreateTime(now);
            int duration = config.getPlayerSessionDuration() * 1000;
            session.setExpireTime(now + duration);
            sessionDAO.insert(session);
            LoginOut out = new LoginOut();

            out.setPlayerId(player.getId().toString());
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
            throw new ServiceException(ErrorType.DATABASE_ERROR);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException(ErrorType.PASSWORD_ENCRYPTION_ERROR);
        }
    }

    /**
     * Player Logout Service
     * @param playerId
     * @param sessionId
     * @param sessionToken
     * @return
     * @throws ServiceException
     */
    public LogoutOut logout(String playerId, String sessionId, String sessionToken) throws ServiceException {
        sessionCheck(playerId, sessionId, sessionToken);
        try {
            sessionDAO.delete(sessionId);
            LogoutOut out = new LogoutOut();
            out.setPlayerId(playerId);
            out.setMessage("Logged Out");
            return out;
        } catch (PersistenceException e) {
            throw new ServiceException(ErrorType.DATABASE_ERROR);
        }
    }

    /**
     * Player Account Service
     * @param playerId
     * @param sessionId
     * @param sessionToken
     * @return
     * @throws ServiceException
     */
    public AccountOut account(String playerId, String sessionId, String sessionToken) throws ServiceException {
        sessionCheck(playerId, sessionId, sessionToken);
        try {
            AccountEntity entity = accountDAO.getByPlayerId(playerId);
            AccountOut out = new AccountOut();
            out.setFirstName(entity.getFirstName());
            out.setLastName(entity.getLastName());
            return out;
        } catch (PersistenceException e) {
            throw new ServiceException(ErrorType.DATABASE_ERROR);
        }
    }

    /**
     * Player Wallet Service
     * @param playerId
     * @param sessionId
     * @param sessionToken
     * @return
     * @throws ServiceException
     */
    public WalletOut wallet(String playerId, String sessionId, String sessionToken) throws ServiceException {
        sessionCheck(playerId, sessionId, sessionToken);
        try {
            PlayerEntity player = playerDAO.get(playerId);

            // Create a wallet if not exists
            if (player.getWallet() == null) {
                WalletEntity wallet = new WalletEntity();
                wallet.setBalance(0);
                wallet.setPlayer(player);
                player.setWallet(wallet);
            }
            WalletOut out = new WalletOut();
            out.setPlayerId(playerId);
            out.setBalance(player.getWallet().getBalance());
            return out;
        } catch (PersistenceException e) {
            throw new ServiceException(ErrorType.DATABASE_ERROR);
        }
    }

    /**
     * Player Wallet Stake Service
     * @param playerId
     * @param sessionId
     * @param sessionToken
     * @param amount
     * @return
     * @throws ServiceException
     */
    public StakeOut stake(String playerId, String sessionId, String sessionToken, long amount) throws ServiceException {
        sessionCheck(playerId, sessionId, sessionToken);
        try {
            WalletEntity wallet = walletDAO.getByPlayerId(playerId);
            long balance = wallet.getBalance() - amount;
            if (amount <= 0) throw new ServiceException(ErrorType.INVALID_AMOUNT);
            if (balance < 0) throw new ServiceException(ErrorType.INSUFFICIENT_FUNDS);
            wallet.setBalance(balance);
            walletDAO.insert(wallet);

            StakeOut out = new StakeOut();
            out.setPlayerId(playerId);
            out.setNickname(wallet.getPlayer().getNickname());
            out.setBalance(wallet.getBalance());
            out.setAmount(amount);
            return out;
        } catch (PersistenceException e) {
            throw new ServiceException(ErrorType.DATABASE_ERROR);
        }
    }

    /**
     * Player Wallet Win Service
     * @param playerId
     * @param sessionId
     * @param sessionToken
     * @param amount
     * @return
     * @throws ServiceException
     */
    public WinOut win(String playerId, String sessionId, String sessionToken, long amount) throws ServiceException {
        sessionCheck(playerId, sessionId, sessionToken);
        try {
            WalletEntity wallet = walletDAO.getByPlayerId(playerId);
            long balance = wallet.getBalance() + amount;
            if (amount <= 0) throw new ServiceException(ErrorType.INVALID_AMOUNT);
            wallet.setBalance(balance);
            walletDAO.insert(wallet);

            WinOut out = new WinOut();
            out.setPlayerId(playerId);
            out.setNickname(wallet.getPlayer().getNickname());
            out.setBalance(wallet.getBalance());
            out.setAmount(amount);
            return out;
        } catch (PersistenceException e) {
            throw new ServiceException(ErrorType.DATABASE_ERROR);
        }
    }

    /**
     * Player Wallet Refund Service
     * @param playerId
     * @param sessionId
     * @param sessionToken
     * @param amount
     * @return
     * @throws ServiceException
     */
    public RefundOut refund(String playerId, String sessionId, String sessionToken, long amount) throws ServiceException {
        sessionCheck(playerId, sessionId, sessionToken);
        try {
            WalletEntity wallet = walletDAO.getByPlayerId(playerId);
            long balance = wallet.getBalance() + amount;
            if (amount <= 0) throw new ServiceException(ErrorType.INVALID_AMOUNT);
            wallet.setBalance(balance);
            walletDAO.insert(wallet);

            RefundOut out = new RefundOut();
            out.setPlayerId(playerId);
            out.setNickname(wallet.getPlayer().getNickname());
            out.setBalance(wallet.getBalance());
            out.setAmount(amount);
            return out;
        } catch (PersistenceException e) {
            throw new ServiceException(ErrorType.DATABASE_ERROR);
        }
    }

    /**
     * Player Session Check
     * @param playerId
     * @param sessionId
     * @param sessionToken
     * @throws ServiceException
     */
    private void sessionCheck(String playerId, String sessionId, String sessionToken) throws ServiceException {
        try {
            SessionEntity session = sessionDAO.getByIdAndToken(sessionId, sessionToken);
        } catch (PersistenceException e) {
            throw new ServiceException(ErrorType.DATABASE_ERROR);
        }
    }

    public SessionRefreshOut sessionRefresh(String playerId, String sessionId, String sessionToken) throws ServiceException {
        try {
            SessionEntity entity = sessionDAO.getByIdAndToken(sessionId, sessionToken);
            entity.setId(UUID.randomUUID().toString());
            entity.setToken(UUID.randomUUID().toString());
            long now = System.currentTimeMillis();
            entity.setCreateTime(now);
            int duration = config.getPlayerSessionDuration() * 1000;
            entity.setExpireTime(now + duration);

            SessionRefreshOut out = new SessionRefreshOut();
            out.setPlayerId(playerId);
            out.setSessionId(entity.getId());
            out.setSessionToken(entity.getToken());
            out.setSessionCreateTime(entity.getCreateTime());
            out.setSessionExpireTime(entity.getExpireTime());

            return out;
        } catch (PersistenceException e) {
            throw new ServiceException(ErrorType.DATABASE_ERROR);
        }
    }
}
