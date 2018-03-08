package jeevsspring.wildfly.poker.manager.game.table;

import org.jboss.logging.Logger;

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

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    private Map<String, Queue<TableAction>> map;

    @PostConstruct
    public void init() {
        logger.trace("init()");
        map = new HashMap<>();
    }

    public boolean insert(TableActionType actionType, String tableId, String playerId, String option) {
        logger.trace("insert(" + actionType + ", " + tableId + ", " + playerId + ", " + option + ")");
        if (!map.containsKey(tableId)) map.put(tableId, new ArrayDeque<>());
        TableAction hand = new TableAction(actionType, playerId, option);
        return map.get(tableId).offer(hand);
    }

    public Queue<TableAction> poll(String tableId) {
        logger.trace("poll(" + tableId + ")");
        return map.get(tableId);
    }

}
