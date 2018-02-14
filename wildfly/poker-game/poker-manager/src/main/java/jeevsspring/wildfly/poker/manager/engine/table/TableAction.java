package jeevsspring.wildfly.poker.manager.engine.table;

/**
 * @author Marco Romagnolo
 */
public class TableAction {

    private TableActionType type;

    TableAction(TableActionType type, String playerId) {
        this.type = type;
        switch (type) {
            case BUY_IN:
                break;
            case BUY_OUT:
                break;
        }
    }

    public TableAction(TableActionType actionType, String playerId, long amount) {

    }

    public TableActionType getType() {
        return type;
    }

}
