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
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * @author Marco Romagnolo
 */
@ApplicationScoped
@Transactional
public class PlayerService {

    //JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

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
     * @throws BackOfficeException
     */
    public LoginOut login(String username, String password) throws BackOfficeException, AuthenticationException {
        logger.debug("login(" + username + ", " + password + ")");

        // get Player by username and password
        PlayerEntity player;
        try {
            // MD5 Hash of Password
            byte[] digest = MessageDigest.getInstance("MD5").digest(password.getBytes());
            String hash = DatatypeConverter.printHexBinary(digest).toLowerCase();
            logger.debug("login() hash: " + hash + ")");
            player = playerDAO.getByUsernameAndPassword(username, hash);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BackOfficeException();
        }

        // Check Player
        if (player == null) {
            throw new AuthenticationException();
        }

        try {
            // Create Session if not exists
            if (player.getSession() == null) {
                SessionEntity session = new SessionEntity();
                session.setId(UUID.randomUUID().toString());
                session.setToken(UUID.randomUUID().toString());
                session.setPlayer(player);
                long now = System.currentTimeMillis();
                session.setCreateTime(now);
                int duration = config.getPlayerSessionDuration() * 1000;
                session.setExpireTime(now + duration);
                sessionDAO.insert(session);
                player.setSession(session);
            }

            // Create Wallet if not exists
            if (player.getWallet() == null) {
                WalletEntity wallet = new WalletEntity();
                wallet.setPlayer(player);
                wallet.setBalance(0);
                walletDAO.insert(wallet);
                player.setWallet(wallet);
            }

            //Set Player
            LoginOut out = new LoginOut();
            out.setPlayerId(player.getId().toString());
            out.setNickname(player.getNickname());
            out.setSessionId(player.getSession().getId());
            out.setSessionToken(player.getSession().getToken());
            out.setSessionCreateTime(player.getSession().getCreateTime());
            out.setSessionExpireTime(player.getSession().getCreateTime());

            // Set Account
            out.setFirstName(player.getAccount().getFirstName());
            out.setLastName(player.getAccount().getLastName());

            // Set Wallet
            out.setBalance(player.getWallet().getBalance());
            return out;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BackOfficeException();
        }


    }

    /**
     * Player Logout Service
     * @param playerId
     * @param sessionId
     * @param sessionToken
     * @return
     * @throws BackOfficeException
     */
    public LogoutOut logout(String playerId, String sessionId, String sessionToken) throws BackOfficeException {
        sessionCheck(playerId, sessionId, sessionToken);
        try {
            sessionDAO.delete(sessionId);
            LogoutOut out = new LogoutOut();
            out.setPlayerId(playerId);
            out.setMessage("Logged Out");
            return out;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BackOfficeException();
        }
    }

    /**
     * Player Account Service
     * @param playerId
     * @param sessionId
     * @param sessionToken
     * @return
     * @throws BackOfficeException
     */
    public AccountOut account(String playerId, String sessionId, String sessionToken) throws BackOfficeException {
        sessionCheck(playerId, sessionId, sessionToken);
        try {
            AccountEntity entity = accountDAO.getByPlayerId(playerId);
            AccountOut out = new AccountOut();
            out.setFirstName(entity.getFirstName());
            out.setLastName(entity.getLastName());
            return out;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BackOfficeException();
        }
    }

    /**
     * Player Wallet Service
     * @param playerId
     * @param sessionId
     * @param sessionToken
     * @return
     * @throws BackOfficeException
     */
    public WalletOut wallet(String playerId, String sessionId, String sessionToken) throws BackOfficeException {
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
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BackOfficeException();
        }
    }

    /**
     * Player Wallet Stake Service
     * @param playerId
     * @param sessionId
     * @param sessionToken
     * @param amount
     * @return
     * @throws BackOfficeException
     */
    public StakeOut stake(String playerId, String sessionId, String sessionToken, long amount) throws BackOfficeException {
        sessionCheck(playerId, sessionId, sessionToken);
        try {
            WalletEntity wallet = walletDAO.getByPlayerId(playerId);
            long balance = wallet.getBalance() - amount;
            if (amount <= 0) {
                logger.error("stake(" + playerId + ", " + sessionId + ", " + sessionToken + ", " + amount + ", " +  ") Invalid Amount");
                throw new BackOfficeException();
            }
            if (balance < 0) {
                logger.error("stake(" + playerId + ", " + sessionId + ", " + sessionToken + ", " + amount + ", " +  ") Insufficient Funds: " + balance);
                throw new BackOfficeException();
            }
            wallet.setBalance(balance);
            walletDAO.insert(wallet);

            StakeOut out = new StakeOut();
            out.setPlayerId(playerId);
            out.setNickname(wallet.getPlayer().getNickname());
            out.setBalance(wallet.getBalance());
            out.setAmount(amount);
            return out;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BackOfficeException();
        }
    }

    /**
     * Player Wallet Win Service
     * @param playerId
     * @param sessionId
     * @param sessionToken
     * @param amount
     * @return
     * @throws BackOfficeException
     */
    public WinOut win(String playerId, String sessionId, String sessionToken, long amount) throws BackOfficeException {
        sessionCheck(playerId, sessionId, sessionToken);
        try {
            WalletEntity wallet = walletDAO.getByPlayerId(playerId);
            long balance = wallet.getBalance() + amount;
            if (amount <= 0) {
                logger.error("win(" + playerId + ", " + sessionId + ", " + sessionToken + ", " + amount + ", " +  ") Invalid Amount");
                throw new BackOfficeException();
            }
            wallet.setBalance(balance);
            walletDAO.insert(wallet);

            WinOut out = new WinOut();
            out.setPlayerId(playerId);
            out.setNickname(wallet.getPlayer().getNickname());
            out.setBalance(wallet.getBalance());
            out.setAmount(amount);
            return out;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BackOfficeException();
        }
    }

    /**
     * Player Wallet Refund Service
     * @param playerId
     * @param sessionId
     * @param sessionToken
     * @param amount
     * @return
     * @throws BackOfficeException
     */
    public RefundOut refund(String playerId, String sessionId, String sessionToken, long amount) throws BackOfficeException {
        sessionCheck(playerId, sessionId, sessionToken);
        try {
            WalletEntity wallet = walletDAO.getByPlayerId(playerId);
            long balance = wallet.getBalance() + amount;
            if (amount <= 0) {
                logger.error("refund(" + playerId + ", " + sessionId + ", " + sessionToken + ", " + amount + ", " +  ") Invalid Amount");
                throw new BackOfficeException();
            }
            wallet.setBalance(balance);
            walletDAO.insert(wallet);

            RefundOut out = new RefundOut();
            out.setPlayerId(playerId);
            out.setNickname(wallet.getPlayer().getNickname());
            out.setBalance(wallet.getBalance());
            out.setAmount(amount);
            return out;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BackOfficeException();
        }
    }

    /**
     * Player Session Check
     * @param playerId
     * @param sessionId
     * @param sessionToken
     * @throws BackOfficeException
     */
    private void sessionCheck(String playerId, String sessionId, String sessionToken) throws BackOfficeException {
        try {
            SessionEntity session = sessionDAO.getByIdAndToken(sessionId, sessionToken);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BackOfficeException();
        }
    }

    public SessionRefreshOut sessionRefresh(String playerId, String sessionId, String sessionToken) throws BackOfficeException {
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
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new BackOfficeException();
        }
    }
}
