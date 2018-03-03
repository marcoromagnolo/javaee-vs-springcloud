package jeevsspring.wildfly.poker.manager.engine.table;

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
        logger.trace("TableAction :: Constructor(" + actionType + ", " + playerId + ", " + option + ", " + ")");
        this.actionType = actionType;
        this.playerId = playerId;
        this.option = option;
    }

    public TableActionType getActionType() {
        logger.trace("TableAction :: getActionType()");
        return actionType;
    }

    public String getPlayerId() {
        logger.trace("TableAction :: getPlayerId()");
        return playerId;
    }

    public String getOption() {
        logger.trace("TableAction :: getOption()");
        return option;
    }
}
