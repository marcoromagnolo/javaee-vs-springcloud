package jeevsspring.spring.poker.manager.player.manager;

import jeevsspring.spring.poker.manager.player.bo.BOClient;
import jeevsspring.spring.poker.manager.player.bo.BOException;
import jeevsspring.spring.poker.manager.player.bo.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marco Romagnolo
 */
@Service
public class PlayerManager {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass().toString());

    private Map<String, String> sessions;

    @Autowired
    private BOClient boClient;

    @PostConstruct
    public void init() {
        logger.log(Level.FINEST, "init()");
        this.sessions = new HashMap<>();
    }

    /**
     * Get Player Id from player sessions
     * @param sessionId
     * @return PlayerId
     */
    public String getPlayerId(String sessionId) throws GameException {
        logger.log(Level.FINEST, "getPlayerId(" + sessionId + ")");
        if (!sessions.containsKey(sessionId)) {
            logger.log(Level.SEVERE, "getPlayerId() Cannot find sessionId: " + sessionId);
            throw new GameException(ErrorCode.INVALID_SESSION);
        }
        return sessions.get(sessionId);
    }

    /**
     * Create new player session
     * @param playerId
     * @param sessionId
     * @return PlayerId
     */
    private String createPlayerSession(String playerId, String sessionId) {
        logger.log(Level.FINEST, "createPlayerSession(" + playerId + ", " + sessionId + ")");
        return sessions.put(playerId, sessionId);
    }

    /**
     * Drop player session
     * @param sessionId
     * @return PlayerId
     */
    private String dropPlayerSession(String sessionId) {
        logger.log(Level.FINEST, "dropPlayerSession(" + sessionId + ")");
        return sessions.remove(sessionId);
    }

    /**
     * Plyer Login
     * @param username
     * @param password
     * @return
     */
    public BOLoginOut login(String username, String password) throws BOException {
        logger.log(Level.FINEST, "login(" + username + ", " + password + ")");

        // Call BO Login Service
        BOLoginIn boLoginIn = new BOLoginIn();
        boLoginIn.setUsername(username);
        boLoginIn.setPassword(password);
        BOLoginOut boLoginOut = boClient.login(boLoginIn);

        // Create Player Session
        String playerId = boLoginOut.getPlayerId();
        String sessionId = boLoginOut.getSessionId();
        createPlayerSession(playerId, sessionId);

        return boLoginOut;
    }

    /**
     * Player Logout
     * @param sessionId
     * @return
     */
    public BOLogoutOut logout(String sessionId, String token) throws BOException {
        logger.log(Level.FINEST, "logout(" + sessionId + ", " + token + ")");

        // Call BO Logout Service
        BOLogoutIn boLogoutIn = new BOLogoutIn();
        boLogoutIn.setSessionId(sessionId);
        boLogoutIn.setSessionToken(token);
        BOLogoutOut boLogoutOut = boClient.logout(boLogoutIn);

        // Destroy Player Session
        dropPlayerSession(sessionId);

        return boLogoutOut;
    }

    public BOWalletOut getWallet(String sessionId, String token) throws BOException {
        logger.log(Level.FINEST, "getWallet(" + sessionId + ", " + token + ")");

        // Call BO Wallet Service
        BOWalletIn boWalletIn = new BOWalletIn();
        boWalletIn.setSessionId(sessionId);
        boWalletIn.setSessionToken(token);
        BOWalletOut boWalletOut = boClient.wallet(boWalletIn);

        return boWalletOut;
    }

    public BOAccountOut getAccount(String sessionId, String token) throws BOException {
        logger.log(Level.FINEST, "logout(" + sessionId + ", " + token + ")");

        // Call BO Account Service
        BOAccountIn accountIn = new BOAccountIn();
        accountIn.setSessionId(sessionId);
        accountIn.setSessionToken(token);
        BOAccountOut accountOut = boClient.account(accountIn);

        return accountOut;
    }
}
