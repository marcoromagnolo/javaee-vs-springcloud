package jeevsspring.wildfly.poker.manager.engine.player;

import jeevsspring.wildfly.poker.manager.engine.table.Seats;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Turns {

    private int numberOfPlayers;
    private int numberOfSeats;
    private List<Integer> indexes = new ArrayList<>();

    public Turns(int numberOfPlayers, Seats seats) {
        this.numberOfPlayers = numberOfPlayers;
        this.numberOfSeats = seats.size();
        for (int i = 0; i < numberOfSeats; i++) {
            String playerId = seats.get(i);
            if (playerId != null) {

                // {Index 0, Seat 0}, {Index 1, Seat 3}, {Index 2, Seat 5},
                indexes.add(i);
            }
        }
    }

    public int getSeat(int index) {
        return indexes.get(index);
    }

    /**
     * Random player index
     * @return
     */
    public int randomIndex() {
        Random r = new Random();
        return r.nextInt(numberOfPlayers);
    }

    /**
     * Next player index of the input index
     * @param index
     * @return
     */
    public int nextIndex(int index) {
        if (index < 0) index = 0;
        return (index + 1) % numberOfPlayers;
    }

    /**
     * Prepare hand by assigning dealer player to index 0
     * SmallBlind to index 1 and BigBlind to index 2
     * Player that have the turn have the index 3 if number of player is >= 3
     * @param index
     * @return list of turns
     */
    public List<Integer> generate(int index) {
        List<Integer> turns = new ArrayList<>();
        for (Integer seat : indexes) {
            index = nextIndex(index);
            turns.add(index);
        }
        return turns;
    }
}
