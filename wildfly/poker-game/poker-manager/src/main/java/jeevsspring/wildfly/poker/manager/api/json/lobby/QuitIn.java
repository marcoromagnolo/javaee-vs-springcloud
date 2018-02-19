package jeevsspring.wildfly.poker.manager.api.json.lobby;

import jeevsspring.wildfly.poker.manager.api.json.PlayerSessionIn;

public class QuitIn extends PlayerSessionIn {

    private String tableId;

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }
}
