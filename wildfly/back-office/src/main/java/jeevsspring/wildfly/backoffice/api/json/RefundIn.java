package jeevsspring.wildfly.backoffice.api.json;

public class RefundIn extends SessionIn {

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
        return "RefundIn{" +
                "playerId='" + playerId + '\'' +
                ", amount=" + amount +
                "} " + super.toString();
    }
}
