package jeevsspring.wildfly.poker.manager.bo.json;

/**
 * @author Marco Romagnolo
 */
public class BOWalletIn extends BOSessionIn {

    private String playerId;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    @Override
    public String toString() {
        return "BOWalletIn{" +
                "playerId='" + playerId + '\'' +
                "} " + super.toString();
    }
}
