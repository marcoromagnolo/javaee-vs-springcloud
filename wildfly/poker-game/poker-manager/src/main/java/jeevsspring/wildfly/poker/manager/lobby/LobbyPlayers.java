package jeevsspring.wildfly.poker.manager.lobby;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marco Romagnolo
 */
@Singleton
@LocalBean
public class LobbyPlayers {

    private Map<String, String> sessions;

    @PostConstruct
    public void init() {
        this.sessions = new HashMap<>();
    }

    /**
     * Get Player Id from player sessions
     * @param sessionId
     * @return PlayerId
     */
    public String getPlayerId(String sessionId) {
        return sessions.get(sessionId);
    }

    /**
     * Create new player session
     * @param playerId
     * @param sessionId
     * @return PlayerId
     */
    public String createPlayerSession(String playerId, String sessionId) {
        return sessions.put(playerId, sessionId);
    }

    /**
     * Drop player session
     * @param sessionId
     * @return PlayerId
     */
    public String dropPlayerSession(String sessionId) {
        return sessions.remove(sessionId);
    }

    /**
     * Plyer Login
     * @param sessionId
     * @param playerId
     * @return
     */
    public String login(String sessionId, String playerId) {
        return sessions.put(sessionId, playerId);
    }

    /**
     * Player Logout
     * @param sessionId
     * @return
     */
    public String logout(String sessionId) {
        return sessions.remove(sessionId);
    }

}