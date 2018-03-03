package jeevsspring.wildfly.poker.manager.engine.table;

import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.List;

public class Seats {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    // List of PlayerId indexed by seat
    private List<String> list;

    // Number of Players
    private int numberOfPlayers;

    /**
     * Constructor
     * @param numberOfSeats
     */
    public Seats(int numberOfSeats) {
        logger.trace("Seats :: Constructor(" + numberOfSeats + ")");
        list = new ArrayList<>(numberOfSeats);
        numberOfPlayers = 0;
    }

    /**
     * Get PlayerId by index
     * @param index
     * @return
     */
    public String get(int index) {
        logger.trace("Seats :: get(" + index + ")");
        return list.get(index);
    }

    /**
     * Get Index of PlayerId
     * @param playerId
     * @return
     */
    public int indexOf(String playerId) {
        logger.trace("Seats :: indexOf(" + playerId + ")");
        return list.indexOf(playerId);
    }

    /**
     * Add a PlayerId to the list with custom index
     * @param index
     * @param playerId
     */
    public void add(int index, String playerId) {
        logger.trace("Seats :: add(" + index + ", " + playerId + ")");
        list.add(index, playerId);
        numberOfPlayers++;
    }

    /**
     * Remove PlayerId from list by index
     * @param index
     */
    public void remove(int index) {
        logger.trace("Seats :: remove(" + index + ")");
        list.add(index, null);
        numberOfPlayers--;
    }

    /**
     * Get size of list
     * @return
     */
    public int size() {
        logger.trace("Seats :: size()");
        return list.size();
    }

    /**
     * Get number of players
     * @return
     */
    public int getNumberOfPlayers() {
        logger.trace("Seats :: getNumberOfPlayers()");
        return numberOfPlayers;
    }

    /**
     * Convert list to array
     * @return
     */
    public String[] toArray() {
        logger.trace("Seats :: toArray()");
        return list.toArray(new String[list.size()]);
    }
}
