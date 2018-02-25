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
public class GameActions <E extends GameAction> {

    private Map<String, Queue<E>> actions;

    @PostConstruct
    public void init() {
        this.actions = new HashMap<>();
    }

    public Queue<E> get(String tableId, String playerId) {
        return actions.get(tableId);
    }

    public void add(String tableId, E action) {
        actions.get(tableId).offer(action);
    }

    public void addAll(String tableId, Queue<E> queue) {
        actions.get(tableId).addAll(queue);
    }
}
