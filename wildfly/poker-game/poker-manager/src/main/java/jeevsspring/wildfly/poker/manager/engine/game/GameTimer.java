package jeevsspring.wildfly.poker.manager.engine.game;

import org.jboss.logging.Logger;

import java.util.HashMap;
import java.util.Map;

public class GameTimer {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    private final Map<String, Long> timers;

    private final long limit;

    public GameTimer(long limit) {
        this.limit = limit;
        timers = new HashMap<>();
    }

    public boolean isOnTime(String playerId) {
        if (!timers.containsKey(playerId)) {
            timers.put(playerId, System.currentTimeMillis());
        }
        long now = System.currentTimeMillis();
        long start = timers.get(playerId);
        return  (start + limit) > now;
    }

    public void reset(String playerId) {
        timers.remove(playerId);
    }
}
