package jeevsspring.wildfly.poker.manager.api.json.player;

public class WalletOut extends PlayerSessionOut {

    private long balance;

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "WalletOut{" +
                "balance=" + balance +
                "} " + super.toString();
    }
}
