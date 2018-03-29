package jeevsspring.wildfly.poker.manager.api.json.table;

import jeevsspring.wildfly.poker.manager.api.json.player.PlayerSessionIn;

public class BuyinIn extends PlayerSessionIn {

    private String tableId;

    private String amount;

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "BuyinIn{" +
                "tableId='" + tableId + '\'' +
                ", amount='" + amount + '\'' +
                "} " + super.toString();
    }
}
