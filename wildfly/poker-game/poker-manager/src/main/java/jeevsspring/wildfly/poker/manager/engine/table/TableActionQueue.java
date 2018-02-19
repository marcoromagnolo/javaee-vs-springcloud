package jeevsspring.wildfly.poker.manager.engine.table;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

@Singleton
@LocalBean
public class TableActionQueue {

    private Map<String, Queue<TableAction>> actions;

    @PostConstruct
    public void init() {
        actions = new HashMap<>();
    }

    public boolean isEmpty(String tableId) {
        return actions.get(tableId).isEmpty();
    }

    public boolean insert(TableActionType actionType, String tableId, String playerId, Long amount) {
        TableAction hand = new TableAction(actionType, tableId, playerId, amount);
        return actions.get(tableId).offer(hand);
    }

    public TableAction examine(String tableId) {
        return actions.get(tableId).peek();
    }

    public TableAction poll(String tableId) {
        return actions.get(tableId).poll();
    }

}
