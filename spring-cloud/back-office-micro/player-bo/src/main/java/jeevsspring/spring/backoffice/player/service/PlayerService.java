package jeevsspring.spring.backoffice.player.service;

import jeevsspring.spring.backoffice.player.api.json.*;
import jeevsspring.spring.backoffice.player.dao.AccountDAO;
import jeevsspring.spring.backoffice.player.dao.PlayerDAO;
import jeevsspring.spring.backoffice.player.dao.SessionDAO;
import jeevsspring.spring.backoffice.player.dao.WalletDAO;
import jeevsspring.spring.backoffice.player.entity.AccountEntity;
import jeevsspring.spring.backoffice.player.entity.PlayerEntity;
import jeevsspring.spring.backoffice.player.entity.SessionEntity;
import jeevsspring.spring.backoffice.player.entity.WalletEntity;
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
public class PlayerService {

    //JBoss Logger
    private final Logger logger = Logger.getLogger(getClass().toString());

    @Autowired
    private WalletDAO walletDAO;

    @Autowired
    private PlayerDAO playerDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private SessionDAO sessionDAO;

    @Autowired
    private Environment env;

    /**
     * Player Login Service
     * @param username
     * @param password
     * @return
     */
    public LoginOut login(String username, String password) throws AuthenticationException, InconsistentDataException {
        logger.log(Level.FINE, "login(" + username + ", " + password + ")");

        // Calculate Hash
        String hash = HashPassword.hash(password);

        // Get Player by username and password
        PlayerEntity player = playerDAO.getByUsernameAndPassword(username, hash);
        if (player == null) {
            throw new AuthenticationException();
        }

        // Create Session if not exists
        if (player.getSession() == null) {
            SessionEntity session = new SessionEntity();
            session.setId(UUID.randomUUID().toString());
            session.setToken(UUID.randomUUID().toString());
            session.setPlayer(player);
            long now = System.currentTimeMillis();
            session.setCreateTime(now);
            int duration = Integer.parseInt(env.getProperty("player.session.duration")) * 1000;
            session.setExpireTime(now + duration);
            sessionDAO.save(session);
            player.setSession(session);
        }

        // Create Wallet if not exists
        if (player.getWallet() == null) {
            WalletEntity wallet = new WalletEntity();
            wallet.setPlayer(player);
            wallet.setBalance(0);
            walletDAO.save(wallet);
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
        if (player.getAccount() == null) throw new InconsistentDataException("Player Account cannot be null");
        out.setFirstName(player.getAccount().getFirstName());
        out.setLastName(player.getAccount().getLastName());

        // Set Wallet
        if (player.getWallet() == null) throw new InconsistentDataException("Player Wallet cannot be null");
        out.setBalance(player.getWallet().getBalance());
        return out;
    }

    /**
     * Player Logout Service
     * @param playerId
     * @param sessionId
     * @param sessionToken
     * @return
     */
    public LogoutOut logout(String playerId, String sessionId, String sessionToken) throws InvalidSessionException {
        sessionCheck(playerId, sessionId, sessionToken);

        sessionDAO.delete(sessionId);
        LogoutOut out = new LogoutOut();
        out.setPlayerId(playerId);
        out.setMessage("Logged Out");
        return out;
    }

    /**
     * Player Account Service
     * @param playerId
     * @param sessionId
     * @param sessionToken
     * @return
     */
    public AccountOut account(String playerId, String sessionId, String sessionToken) throws InvalidSessionException {
        sessionCheck(playerId, sessionId, sessionToken);

        AccountEntity entity = accountDAO.findByPlayerId(playerId);
        AccountOut out = new AccountOut();
        out.setFirstName(entity.getFirstName());
        out.setLastName(entity.getLastName());
        return out;
    }

    /**
     * Player Wallet Service
     * @param playerId
     * @param sessionId
     * @param sessionToken
     * @return
     */
    public WalletOut wallet(String playerId, String sessionId, String sessionToken) throws InvalidSessionException {
        sessionCheck(playerId, sessionId, sessionToken);

        PlayerEntity player = playerDAO.findOne(Long.parseLong(playerId));
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
    }

    /**
     * Player Wallet Stake Service
     * @param playerId
     * @param sessionId
     * @param sessionToken
     * @param amount
     * @return
     */
    public StakeOut stake(String playerId, String sessionId, String sessionToken, long amount) throws InvalidAmountException, InsufficientFundsException, InvalidSessionException {
        sessionCheck(playerId, sessionId, sessionToken);

        if (amount <= 0) {
            logger.log(Level.SEVERE, "stake(" + playerId + ", " + sessionId + ", " + sessionToken + ", " + amount + ", " +  ") Invalid Amount");
            throw new InvalidAmountException();
        }

        WalletEntity wallet = walletDAO.findByPlayerId(playerId);
        long balance = wallet.getBalance() - amount;
        if (balance < 0) {
            logger.log(Level.SEVERE, "stake(" + playerId + ", " + sessionId + ", " + sessionToken + ", " + amount + ", " +  ") Insufficient Funds: " + balance);
            throw new InsufficientFundsException();
        }
        wallet.setBalance(balance);
        walletDAO.save(wallet);

        StakeOut out = new StakeOut();
        out.setPlayerId(playerId);
        out.setNickname(wallet.getPlayer().getNickname());
        out.setBalance(wallet.getBalance());
        out.setAmount(amount);
        return out;
    }

    /**
     * Player Wallet Win Service
     * @param playerId
     * @param sessionId
     * @param sessionToken
     * @param amount
     * @return
     */
    public WinOut win(String playerId, String sessionId, String sessionToken, long amount) throws InvalidAmountException, InvalidSessionException {
        sessionCheck(playerId, sessionId, sessionToken);

        if (amount <= 0) {
            logger.log(Level.SEVERE, "win(" + playerId + ", " + sessionId + ", " + sessionToken + ", " + amount + ", " +  ") Invalid Amount");
            throw new InvalidAmountException();
        }

        WalletEntity wallet = walletDAO.findByPlayerId(playerId);
        long balance = wallet.getBalance() + amount;
        wallet.setBalance(balance);
        walletDAO.save(wallet);

        WinOut out = new WinOut();
        out.setPlayerId(playerId);
        out.setNickname(wallet.getPlayer().getNickname());
        out.setBalance(wallet.getBalance());
        out.setAmount(amount);
        return out;
    }

    /**
     * Player Wallet Refund Service
     * @param playerId
     * @param sessionId
     * @param sessionToken
     * @param amount
     * @return
     */
    public RefundOut refund(String playerId, String sessionId, String sessionToken, long amount) throws InvalidAmountException, InvalidSessionException {
        sessionCheck(playerId, sessionId, sessionToken);

        if (amount <= 0) {
            logger.log(Level.SEVERE, "refund(" + playerId + ", " + sessionId + ", " + sessionToken + ", " + amount + ", " +  ") Invalid Amount");
            throw new InvalidAmountException();
        }

        WalletEntity wallet = walletDAO.findByPlayerId(playerId);
        long balance = wallet.getBalance() + amount;
        wallet.setBalance(balance);
        walletDAO.save(wallet);

        RefundOut out = new RefundOut();
        out.setPlayerId(playerId);
        out.setNickname(wallet.getPlayer().getNickname());
        out.setBalance(wallet.getBalance());
        out.setAmount(amount);
        return out;
    }

    /**
     * Player Session Check
     * @param playerId
     * @param sessionId
     * @param sessionToken
     */
    private void sessionCheck(String playerId, String sessionId, String sessionToken) throws InvalidSessionException {
        SessionEntity entity = sessionDAO.findByIdAndToken(sessionId, sessionToken);
        if (entity == null || !entity.getPlayer().getId().toString().equals(playerId)) throw new InvalidSessionException();
    }

    public SessionRefreshOut sessionRefresh(String playerId, String sessionId, String sessionToken) throws InvalidSessionException {
        SessionEntity entity = sessionDAO.findByIdAndToken(sessionId, sessionToken);
        if (entity == null || !entity.getPlayer().getId().toString().equals(playerId)) throw new InvalidSessionException();

        entity.setId(UUID.randomUUID().toString());
        entity.setToken(UUID.randomUUID().toString());
        long now = System.currentTimeMillis();
        entity.setCreateTime(now);
        int duration = Integer.parseInt(env.getProperty("player.session.duration")) * 1000;
        entity.setExpireTime(now + duration);

        SessionRefreshOut out = new SessionRefreshOut();
        out.setPlayerId(playerId);
        out.setSessionId(entity.getId());
        out.setSessionToken(entity.getToken());
        out.setSessionCreateTime(entity.getCreateTime());
        out.setSessionExpireTime(entity.getExpireTime());

        return out;
    }
}
