package jeevsspring.wildfly.poker.manager.engine.game;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

/**
 * @author Marco Romagnolo
 */
@Singleton
@LocalBean
public class GameActions {

    private Map<String, Queue<GameAction>> actions;

    @PostConstruct
    public void init() {
        this.actions = new HashMap<>();
    }

    public Queue<GameAction> get(String tableId) {
        return actions.get(tableId);
    }

    public Queue<GameAction> get(String tableId, String playerId) {
        Queue<GameAction> queue = actions.get(tableId);
        // TODO prepare player data
        return queue;
    }

    public void add(String tableId, GameAction action) {
        get(tableId).offer(action);
    }

    public void addAll(String tableId, Queue<GameAction> actions) {
        get(tableId).addAll(actions);
    }
}
