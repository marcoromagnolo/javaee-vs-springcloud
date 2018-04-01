package jeevsspring.spring.backoffice.player.api.json;

/**
 * @author Marco Romagnolo
 */
public class WalletIn extends SessionIn {

    private String playerId;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    @Override
    public String toString() {
        return "WalletIn{" +
                "playerId='" + playerId + '\'' +
                "} " + super.toString();
    }
}
