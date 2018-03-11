package jeevsspring.wildfly.poker.manager.game.table;

import jeevsspring.wildfly.poker.manager.game.player.Player;
import org.jboss.logging.Logger;

/**
 * @author Marco Romagnolo
 */
public class TableAction {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    private TableActionType actionType;
    private String playerId;
    private Player player;
    private Integer seat;

    public TableAction(TableActionType actionType, String playerId, Integer seat) {
        logger.trace("TableAction(" + actionType + ", " + playerId + ")");
        this.actionType = actionType;
        this.playerId = playerId;
        this.seat = seat;
    }

    public TableAction(TableActionType actionType, String playerId) {
        logger.trace("TableAction(" + actionType + ", " + playerId + ")");
        this.actionType = actionType;
        this.playerId = playerId;
    }

    public TableAction(TableActionType actionType, Player player) {
        logger.trace("TableAction(" + actionType + ", " + player + ")");
        this.actionType = actionType;
        this.player = player;
        this.playerId = player.getId();
    }

    public TableActionType getActionType() {
        logger.trace("getActionType()");
        return actionType;
    }

    public String getPlayerId() {
        logger.trace("getPlayerId()");
        return playerId;
    }

    public Player getPlayer() {
        logger.trace("getPlayer()");
        return player;
    }

    public int getSeat() {
        return seat;
    }
}
