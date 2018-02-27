package jeevsspring.wildfly.poker.manager.engine.hand;

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
public class HandActionQueue {

    private Map<String, Queue<HandAction>> map;

    @PostConstruct
    public void init() {
        map = new HashMap<>();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public boolean insert(HandActionType actionType, String tableId, String handId, String playerId, String option) {
        if (!map.containsKey(tableId)) map.put(tableId, new ArrayDeque<>());
        HandAction hand = new HandAction(actionType, tableId, handId, playerId, option);
        return map.get(tableId).offer(hand);
    }

    public Queue<HandAction> poll(String tableId) {
        return map.get(tableId);
    }

}
