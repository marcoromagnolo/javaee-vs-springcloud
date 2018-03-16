package jeevsspring.wildfly.backoffice.api.json;

public class StakeIn extends SessionIn {

    private String playerId;

    private long amount;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "StakeIn{" +
                "playerId='" + playerId + '\'' +
                ", amount=" + amount +
                "} " + super.toString();
    }
}
