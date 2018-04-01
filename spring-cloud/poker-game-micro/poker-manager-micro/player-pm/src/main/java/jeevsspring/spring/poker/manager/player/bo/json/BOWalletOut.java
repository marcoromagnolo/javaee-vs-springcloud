package jeevsspring.spring.poker.manager.player.bo.json;

/**
 * @author Marco Romagnolo
 */
public class BOWalletOut extends BOSessionOut {

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
        return "BOWalletOut{" +
                "playerId='" + playerId + '\'' +
                ", balance=" + balance +
                "} " + super.toString();
    }
}
