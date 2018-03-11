package jeevsspring.wildfly.poker.manager.game.table;

import jeevsspring.wildfly.poker.manager.game.player.PlayerManager;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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

    @EJB
    private PlayerManager playerManager;

    @PostConstruct
    public void init() {
        logger.trace("init()");
        map = new HashMap<>();
    }

    public boolean sitin(String tableId, String playerId, int seat) {
        logger.debug("insert(" + tableId + ", " + playerId + ")");
        if (!map.containsKey(tableId)) map.put(tableId, new ArrayDeque<>());
        TableAction tableAction = new TableAction(TableActionType.SIT_IN, playerId, seat);
        return map.get(tableId).offer(tableAction);
    }

    public boolean sitout(String tableId, String playerId) {
        logger.debug("insert(" + tableId + ", " + playerId + ")");
        if (!map.containsKey(tableId)) map.put(tableId, new ArrayDeque<>());
        TableAction tableAction = new TableAction(TableActionType.SIT_OUT, playerId);
        return map.get(tableId).offer(tableAction);
    }

    public Queue<TableAction> pop(String tableId) {
        logger.debug("pop(" + tableId + ")");
        return map.remove(tableId);
    }

    public boolean contains(String tableId) {
        logger.trace("contains(" + tableId + ")");
        return map.containsKey(tableId);
    }
}
