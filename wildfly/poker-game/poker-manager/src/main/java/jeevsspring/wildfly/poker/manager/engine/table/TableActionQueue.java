package jeevsspring.wildfly.poker.manager.engine.table;

import jeevsspring.wildfly.poker.manager.engine.game.Game;
import jeevsspring.wildfly.poker.manager.engine.game.Games;
import jeevsspring.wildfly.poker.manager.exception.PMException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

@Singleton
@LocalBean
public class TableActionQueue {

    @EJB
    private Games games;

    private Map<String, Queue<TableAction>> actions;

    @PostConstruct
    public void init() {
        actions = new HashMap<>();
    }

    public boolean isEmpty(String tableId) {
        return actions.get(tableId).isEmpty();
    }

    public boolean insert(TableActionType actionType, String tableId, String playerId, String option) throws PMException {
        Game game = games.get(tableId);
        if (!game.isRunning()) throw new PMException();
        TableAction hand = new TableAction(actionType, playerId, option);
        return actions.get(tableId).offer(hand);
    }

    public TableAction examine(String tableId) {
        return actions.get(tableId).peek();
    }

    public TableAction poll(String tableId) {
        return actions.get(tableId).poll();
    }

}
