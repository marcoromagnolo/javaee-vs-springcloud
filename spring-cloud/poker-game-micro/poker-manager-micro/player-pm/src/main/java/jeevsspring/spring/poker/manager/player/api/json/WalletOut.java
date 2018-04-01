package jeevsspring.spring.poker.manager.player.api.json;

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
