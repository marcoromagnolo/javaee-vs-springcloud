package jeevsspring.wildfly.poker.manager.api.json.lobby;

import jeevsspring.wildfly.poker.manager.api.json.player.PlayerSessionIn;

public class TableSettingsIn extends PlayerSessionIn {

    private String tableId;

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    @Override
    public String toString() {
        return "TableSettingsIn{" +
                "tableId='" + tableId + '\'' +
                '}';
    }
}
