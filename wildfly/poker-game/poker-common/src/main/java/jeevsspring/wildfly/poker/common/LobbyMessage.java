package jeevsspring.wildfly.poker.common;

import java.io.Serializable;

/**
 * @author Marco Romagnolo
 */
public class LobbyMessage implements Serializable {

    private String tableId;
    private LobbyActionType actionType;
    private TableSettings tableSettings;

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public LobbyActionType getActionType() {
        return actionType;
    }

    public void setActionType(LobbyActionType actionType) {
        this.actionType = actionType;
    }

    public TableSettings getTableSettings() {
        return tableSettings;
    }

    public void setTableSettings(TableSettings tableSettings) {
        this.tableSettings = tableSettings;
    }

    @Override
    public String toString() {
        return "LobbyMessage{" +
                "tableId='" + tableId + '\'' +
                ", actionType=" + actionType +
                ", tableSettings=" + tableSettings +
                '}';
    }
}
