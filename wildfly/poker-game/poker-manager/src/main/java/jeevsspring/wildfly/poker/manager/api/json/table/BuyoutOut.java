package jeevsspring.wildfly.poker.manager.api.json.table;

import jeevsspring.wildfly.poker.manager.api.json.player.PlayerSessionOut;

public class BuyoutOut extends PlayerSessionOut {

    private long amount;

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
