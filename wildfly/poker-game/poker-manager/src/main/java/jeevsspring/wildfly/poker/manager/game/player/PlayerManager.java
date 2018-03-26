package jeevsspring.wildfly.poker.manager.game.player;

import jeevsspring.wildfly.poker.manager.bo.BOException;
import jeevsspring.wildfly.poker.manager.bo.BOClient;
import jeevsspring.wildfly.poker.manager.bo.json.*;
import jeevsspring.wildfly.poker.manager.game.ErrorCode;
import jeevsspring.wildfly.poker.manager.game.GameException;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marco Romagnolo
 */
@Singleton
@LocalBean
public class PlayerManager {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    private Map<String, String> sessions;

    @EJB
    private BOClient boClient;

    @PostConstruct
    public void init() {
        logger.trace("init()");
        this.sessions = new HashMap<>();
    }

    /**
     * Get Player Id from player sessions
     * @param sessionId
     * @return PlayerId
     */
    public String getPlayerId(String sessionId) throws GameException {
        logger.trace("getPlayerId(" + sessionId + ")");
        if (!sessions.containsKey(sessionId)) {
            logger.error("getPlayerId() Cannot find sessionId: " + sessionId);
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
        logger.trace("createPlayerSession(" + playerId + ", " + sessionId + ")");
        return sessions.put(playerId, sessionId);
    }

    /**
     * Drop player session
     * @param sessionId
     * @return PlayerId
     */
    private String dropPlayerSession(String sessionId) {
        logger.trace("dropPlayerSession(" + sessionId + ")");
        return sessions.remove(sessionId);
    }

    /**
     * Plyer Login
     * @param username
     * @param password
     * @return
     */
    public BOLoginOut login(String username, String password) throws BOException{
        logger.trace("login(" + username + ", " + password + ")");

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
        logger.trace("logout(" + sessionId + ", " + token + ")");

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
        logger.trace("getWallet(" + sessionId + ", " + token + ")");

        // Call BO Wallet Service
        BOWalletIn boWalletIn = new BOWalletIn();
        boWalletIn.setSessionId(sessionId);
        boWalletIn.setSessionToken(token);
        BOWalletOut boWalletOut = boClient.wallet(boWalletIn);

        return boWalletOut;
    }

    public BOAccountOut getAccount(String sessionId, String token) throws BOException {
        logger.trace("logout(" + sessionId + ", " + token + ")");

        // Call BO Account Service
        BOAccountIn accountIn = new BOAccountIn();
        accountIn.setSessionId(sessionId);
        accountIn.setSessionToken(token);
        BOAccountOut accountOut = boClient.account(accountIn);

        return accountOut;
    }
}
