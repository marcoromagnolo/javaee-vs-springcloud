package jeevsspring.wildfly.poker.manager.engine.table;

/**
 * @author Marco Romagnolo
 */
public class TableAction {

    private TableActionType actionType;
    private String playerId;
    private String option;

    public TableAction(TableActionType actionType, String playerId, String option) {
        this.actionType = actionType;
        this.playerId = playerId;
        this.option = option;
    }

    public TableActionType getActionType() {
        return actionType;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getOption() {
        return option;
    }
}
