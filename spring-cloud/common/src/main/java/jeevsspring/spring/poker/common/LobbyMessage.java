package jeevsspring.spring.poker.common;

import java.io.Serializable;

/**
 * @author Marco Romagnolo
 */
public class LobbyMessage implements Serializable {

    private String tableId;
    private TableSettings tableSettings;

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
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
                ", tableSettings=" + tableSettings +
                '}';
    }
}
