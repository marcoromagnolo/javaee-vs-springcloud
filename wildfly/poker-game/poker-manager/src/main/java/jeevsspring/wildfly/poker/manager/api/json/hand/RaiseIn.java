package jeevsspring.wildfly.poker.manager.api.json.hand;

import jeevsspring.wildfly.poker.manager.api.json.PlayerSessionIn;

public class RaiseIn extends PlayerSessionIn {

    private String tableId;
    private String handId;
    private String amount;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
