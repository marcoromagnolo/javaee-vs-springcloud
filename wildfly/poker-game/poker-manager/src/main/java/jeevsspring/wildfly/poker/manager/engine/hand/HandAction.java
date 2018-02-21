package jeevsspring.wildfly.poker.manager.engine.hand;

public class HandAction {

    private final String tableId;
    private final HandActionType actionType;
    private final String handId;
    private final String playerId;
    private final String option;

    public HandAction(HandActionType actionType, String tabledId,  String handId, String playerId, String option) {
        this.actionType = actionType;
        this.tableId = tabledId;
        this.handId = handId;
        this.playerId = playerId;
        this.option = option;
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

    public String getOption() {
        return option;
    }
}
