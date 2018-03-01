package jeevsspring.wildfly.poker.manager.engine.table;

import java.util.ArrayList;
import java.util.List;

public class Seats {

    // List of PlayerId indexed by seat
    private List<String> list;

    // Number of Players
    private int numberOfPlayers;

    /**
     * Constructor
     * @param numberOfSeats
     */
    public Seats(int numberOfSeats) {
        list = new ArrayList<>(numberOfSeats);
        numberOfPlayers = 0;
    }

    /**
     * Get PlayerId by index
     * @param index
     * @return
     */
    public String get(int index) {
        return list.get(index);
    }

    /**
     * Get Index of PlayerId
     * @param playerId
     * @return
     */
    public int indexOf(String playerId) {
        return list.indexOf(playerId);
    }

    /**
     * Add a PlayerId to the list with custom index
     * @param index
     * @param playerId
     */
    public void add(int index, String playerId) {
        list.add(index, playerId);
        numberOfPlayers++;
    }

    /**
     * Remove PlayerId from list by index
     * @param index
     */
    public void remove(int index) {
        list.add(index, null);
        numberOfPlayers--;
    }

    /**
     * Get size of list
     * @return
     */
    public int size() {
        return list.size();
    }

    /**
     * Get number of players
     * @return
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Convert list to array
     * @return
     */
    public String[] toArray() {
        return list.toArray(new String[list.size()]);
    }
}
