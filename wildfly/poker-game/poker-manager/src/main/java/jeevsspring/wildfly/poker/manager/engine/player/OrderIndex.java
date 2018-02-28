package jeevsspring.wildfly.poker.manager.engine.player;

import jeevsspring.wildfly.poker.manager.engine.table.Seats;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderIndex {

    private int numberOfPlayers;
    private int numberOfSeats;

    // {index : seat} Ex. seats = {2, 3, 7}
    private List<Integer> indexes = new ArrayList<>();
    private int index;

    public OrderIndex(Seats seats) {
        update(seats);
    }

    /**
     * Update Players Index
     * @param seats
     */
    public void update(Seats seats) {
        this.numberOfPlayers = seats.getNumberOfPlayers();
        this.numberOfSeats = seats.size();
        for (int seat = 0; seat < numberOfSeats; seat++) {
            String playerId = seats.get(seat);
            if (playerId != null) {

                // {Index 0, Seat 0}, {Index 1, Seat 3}, {Index 2, Seat 5},
                indexes.add(seat);
            }
        }
    }

    /**
     * Get seat by order number
     * @param index
     * @return
     */
    public Integer getSeatByOrder(int index) {
        for (int seat = 0; seat < indexes.size(); seat++) {
            if (indexes.get(seat) == index) return seat;
        }
        return null;
    }

    /**
     * Random player index order
     * @return
     */
    public void randomize() {
        Random r = new Random();
        index = r.nextInt(numberOfPlayers);
    }

    /**
     * Get list of index orders
     * @return list of order
     */
    public List<Integer> getAll() {
        return indexes;
    }

    /**
     * Get order by seat
     * @param index
     * @return
     */
    public Integer get(int index) {
        return indexes.get(index);
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
        index = (index + 1) % numberOfPlayers;
    }
}
