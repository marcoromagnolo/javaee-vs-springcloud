package jeevsspring.wildfly.poker.manager.engine.player;

import jeevsspring.wildfly.poker.manager.engine.table.Table;

/**
 * @author Marco Romagnolo
 */
public class PlayerAction {

    private final Table table;
    private final Player player;
    private final PlayerActionType actionType;
    private final long amount;

    public PlayerAction(Table table, Player player, PlayerActionType actionType, Long amount) {
        this.table = table;
        this.player = player;
        this.actionType = actionType;
        this.amount = amount;
    }

    public Table getTable() {
        return table;
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerActionType getActionType() {
        return actionType;
    }

    public long getAmount() {
        return amount;
    }

}
