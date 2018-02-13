package jeevsspring.wildfly.poker.manager.engine.table;

/**
 * @author Marco Romagnolo
 */
public class TableAction {

    private TableActionType type;

    TableAction(TableActionType type) {
        this.type = type;
        switch (type) {
            case OPEN:
                break;
            case CLOSE:
                break;
        }
    }

    public TableActionType getType() {
        return type;
    }
}
