package jeevsspring.wildfly.poker.manager.engine.hand;

public class HandAction {

    private final String tableId;
    private final HandActionType actionType;
    private final String handId;
    private final String playerId;
    private final Long amount;

    public HandAction(HandActionType actionType, String tabledId,  String handId, String playerId, Long amount) {
        this.actionType = actionType;
        this.tableId = tabledId;
        this.handId = handId;
        this.playerId = playerId;
        this.amount = amount;
    }

    public HandActionType getActionType() {
        return actionType;
    }

    public String getTableId() {
        return tableId;
    }

    public String getHandId() {
        return handId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public Long getAmount() {
        return amount;
    }
}
