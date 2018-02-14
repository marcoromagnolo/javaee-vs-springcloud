package jeevsspring.wildfly.poker.manager.engine.hand;

import jeevsspring.wildfly.poker.manager.engine.player.Player;
import jeevsspring.wildfly.poker.manager.engine.table.Table;

/**
 * @author Marco Romagnolo
 */
public class HandAction {

    private final Table table;
    private final Player player;
    private final HandActionType actionType;

    public HandAction(Table table, Player player, HandActionType actionType) {
        this.table = table;
        this.player = player;
        this.actionType = actionType;
        this.table.getGame().playerAction(actionType);
    }

    public HandAction(Table table, Player player, HandActionType actionType, long amount) {
        this.table = table;
        this.player = player;
        this.actionType = actionType;
        this.table.getGame().playerAction(actionType, amount);
    }

}
