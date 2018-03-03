package jeevsspring.wildfly.poker.manager.engine.hand;

import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * @author Marco Romagnolo
 */
@Singleton
@LocalBean
public class HandActions {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    private Map<String, Map<String, HandAction>> map;

    @PostConstruct
    public void init() {
        map = new HashMap<>();
    }

    public void insert(HandActionType actionType, String tableId, String handId, String playerId, String option) {
        logger.debug("HandActions :: insert(" + actionType + ", " + tableId + ", " + handId + ", " + playerId + ", "
                + option + ")");
        if (!map.containsKey(tableId)) {
            map.put(tableId, new HashMap<>());
        }
        HandAction hand = new HandAction(actionType, tableId, handId, playerId, option);
        map.get(tableId).put(playerId, hand);
    }

    public Map<String, HandAction> get(String tableId) {
        return map.get(tableId);
    }

}
