package jeevsspring.wildfly.poker.manager.api.json.table;

import jeevsspring.wildfly.poker.manager.api.json.PlayerSessionIn;

public class BuyinIn extends PlayerSessionIn {

    private String tableId;
    private long amount;

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
