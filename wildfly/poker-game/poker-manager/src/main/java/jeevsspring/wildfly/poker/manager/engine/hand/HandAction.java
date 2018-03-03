package jeevsspring.wildfly.poker.manager.engine.hand;

import org.jboss.logging.Logger;

public class HandAction {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    private final String tableId;
    private final HandActionType actionType;
    private final String handId;
    private final String playerId;
    private final String option;
    private final long time;

    public HandAction(HandActionType actionType, String tabledId,  String handId, String playerId, String option) {
        logger.debug("HandAction :: Constructor(" + actionType + ", " + tabledId + ", " + handId + ", " + playerId + ", "
                + option + ")");
        this.actionType = actionType;
        this.tableId = tabledId;
        this.handId = handId;
        this.playerId = playerId;
        this.option = option;
        this.time = System.currentTimeMillis();
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

    public long getTime() {
        return time;
    }
}
