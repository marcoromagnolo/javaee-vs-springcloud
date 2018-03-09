package jeevsspring.wildfly.poker.manager.game.player;

import jeevsspring.wildfly.poker.manager.game.table.Seats;
import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Orders {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    // Number of Players
    private int numberOfPlayers;

    // Number of Seats
    private int numberOfSeats;

    // {index : seat} Ex. seats = {2, 3, 7}
    private List<Integer> list;

    // Current index
    private int index;

    /**
     * Constructor
     * @param seats
     */
    public Orders(Seats seats) {
        logger.debug("Orders(" + seats + ")");
        update(seats);
    }

    /**
     * Update Players Index
     * @param seats
     */
    public void update(Seats seats) {
        logger.debug("update(" + seats + ")");
        this.numberOfPlayers = seats.getNumberOfPlayers();
        this.numberOfSeats = seats.size();
        this.list = new ArrayList<>();
        for (int seat = 0; seat < numberOfSeats; seat++) {
            String playerId = seats.get(seat);
            if (playerId != null) {

                // {Index 0, Seat 0}, {Index 1, Seat 3}, {Index 2, Seat 5},
                list.add(seat);
            }
        }
    }

    /**
     * Get index by seat
     * @param seat
     * @return
     */
    public int indexOf(Integer seat) {
        logger.debug("indexOf(" + seat + ")");
        return list.indexOf(seat);
    }

    /**
     * Random player index
     * @return
     */
    public void randomize() {
        logger.debug("randomize()");
        Random r = new Random();
        index = r.nextInt(numberOfPlayers);
    }

    /**
     * Get list of indexed seats
     * @return list of order
     */
    public List<Integer> getAll() {
        return list;
    }

    /**
     * Get seat by index
     * @param index
     * @return
     */
    public Integer get(int index) {
        return list.get(index);
    }

    /**
     * Get Index
     * @return
     */
    public int current() {
        return index;
    }

    /**
     * Next player index of the input index
     * @return
     */
    public void next() {
        logger.debug("next()");
        index = numberOfPlayers>1 ? (index + 1) % numberOfPlayers : 1;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "numberOfPlayers=" + numberOfPlayers +
                ", numberOfSeats=" + numberOfSeats +
                ", list=" + list +
                ", index=" + index +
                '}';
    }
}
