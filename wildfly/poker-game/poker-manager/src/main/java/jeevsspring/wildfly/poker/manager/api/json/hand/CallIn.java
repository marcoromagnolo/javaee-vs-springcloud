package jeevsspring.wildfly.poker.manager.api.json.hand;

import jeevsspring.wildfly.poker.manager.api.json.player.PlayerSessionIn;

public class CallIn extends PlayerSessionIn {

    private String handId;
    private String tableId;

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getHandId() {
        return handId;
    }

    public void setHandId(String handId) {
        this.handId = handId;
    }

    @Override
    public String toString() {
        return "CallIn{" +
                "handId='" + handId + '\'' +
                ", tableId='" + tableId + '\'' +
                "} " + super.toString();
    }
}
