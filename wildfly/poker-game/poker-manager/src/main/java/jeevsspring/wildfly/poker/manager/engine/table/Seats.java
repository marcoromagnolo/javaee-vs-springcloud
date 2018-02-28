package jeevsspring.wildfly.poker.manager.engine.table;

import java.util.ArrayList;
import java.util.List;

public class Seats {

    private List<String> seats;
    private int numberOfPlayers;

    public Seats(int numberOfSeats) {
        seats = new ArrayList<>(numberOfSeats);
        numberOfPlayers = 0;
    }

    /**
     * Get Player Id
     * @param index
     * @return
     */
    public String get(int index) {
        return seats.get(index);
    }

    /**
     * Get Index by Player Id
     * @param playerId
     * @return
     */
    public Integer getByPlayerId(String playerId) {
        for (int i = 0; i < seats.size(); i++) {
            String id = seats.get(i);
            if (playerId.equals(id)) {
                return i;
            }
        }
        return null;
    }

    public void add(int index, String playerId) {
        seats.add(index, playerId);
        numberOfPlayers++;
    }

    public void remove(int index) {
        seats.add(index, null);
        numberOfPlayers--;
    }

    public int size() {
        return seats.size();
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public String[] toArray() {
        return seats.toArray(new String[seats.size()]);
    }
}
