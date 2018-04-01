package jeevsspring.spring.poker.manager.player.bo.json;

/**
 * @author Marco Romagnolo
 */
public class BOAccountIn extends BOSessionIn {

    private String playerId;

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    @Override
    public String toString() {
        return "BOAccountIn{" +
                "playerId='" + playerId + '\'' +
                "} " + super.toString();
    }
}
