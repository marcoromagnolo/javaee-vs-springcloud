package jeevsspring.wildfly.poker.manager.game.table;

import org.jboss.logging.Logger;

/**
 * @author Marco Romagnolo
 */
public class TableAction {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    private TableActionType actionType;
    private String playerId;
    private String option;

    public TableAction(TableActionType actionType, String playerId, String option) {
        logger.trace("TableAction(" + actionType + ", " + playerId + ", " + option + ", " + ")");
        this.actionType = actionType;
        this.playerId = playerId;
        this.option = option;
    }

    public TableActionType getActionType() {
        logger.trace("getActionType()");
        return actionType;
    }

    public String getPlayerId() {
        logger.trace("getPlayerId()");
        return playerId;
    }

    public String getOption() {
        logger.trace("getOption()");
        return option;
    }

    @Override
    public String toString() {
        return "TableAction{" +
                "actionType=" + actionType +
                ", playerId='" + playerId + '\'' +
                ", option='" + option + '\'' +
                '}';
    }
}
