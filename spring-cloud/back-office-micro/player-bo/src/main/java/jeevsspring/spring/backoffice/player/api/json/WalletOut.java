package jeevsspring.spring.backoffice.player.api.json;

/**
 * @author Marco Romagnolo
 */
public class WalletOut extends SessionOut {

    private String playerId;

    private long balance;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "WalletOut{" +
                "playerId='" + playerId + '\'' +
                ", balance=" + balance +
                "} " + super.toString();
    }
}
