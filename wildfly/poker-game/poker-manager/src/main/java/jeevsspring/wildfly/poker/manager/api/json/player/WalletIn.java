package jeevsspring.wildfly.poker.manager.api.json.player;

public class WalletIn extends PlayerSessionIn {

    private long balance;

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }
}
