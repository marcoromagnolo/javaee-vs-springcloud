package jeevsspring.wildfly.poker.manager.api.json.hand;

import java.util.List;

/**
 * @author Marco Romagnolo
 */
public class PotOut {

    private long value;
    private List<String> players;

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return "PotOut{" +
                "value=" + value +
                ", players=" + players +
                '}';
    }
}
