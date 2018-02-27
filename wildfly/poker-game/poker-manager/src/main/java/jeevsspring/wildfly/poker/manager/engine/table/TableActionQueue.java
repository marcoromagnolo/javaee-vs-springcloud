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

    private Map<String, Queue<TableAction>> map;

    @PostConstruct
    public void init() {
        map = new HashMap<>();
    }

    public boolean isEmpty(String tableId) {
        return map.get(tableId).isEmpty();
    }

    public boolean insert(TableActionType actionType, String tableId, String playerId, String option) {
        if (!map.containsKey(tableId)) map.put(tableId, new ArrayDeque<>());
        TableAction hand = new TableAction(actionType, playerId, option);
        return map.get(tableId).offer(hand);
    }

    public Queue<TableAction> poll(String tableId) {
        return map.get(tableId);
    }

}
