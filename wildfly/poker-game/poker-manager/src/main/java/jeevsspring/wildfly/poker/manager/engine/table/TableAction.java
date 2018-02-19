package jeevsspring.wildfly.poker.manager.engine.table;

/**
 * @author Marco Romagnolo
 */
public class TableAction {

    private TableActionType actionType;
    private String tableId;
    private String playerId;
    private Long amount;

    public TableAction(TableActionType actionType, String tableId, String playerId, Long amount) {
        this.actionType = actionType;
        this.tableId = tableId;
        this.playerId = playerId;
        this.amount = amount;
    }

    public TableActionType getActionType() {
        return actionType;
    }

    public String getTableId() {
        return tableId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public Long getAmount() {
        return amount;
    }
}
