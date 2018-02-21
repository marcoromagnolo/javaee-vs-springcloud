package jeevsspring.wildfly.poker.manager.api.json.hand;

import jeevsspring.wildfly.poker.manager.api.json.PlayerSessionIn;

public class SitinIn extends PlayerSessionIn {

    private String handId;
    private String seat;
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

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }
}
