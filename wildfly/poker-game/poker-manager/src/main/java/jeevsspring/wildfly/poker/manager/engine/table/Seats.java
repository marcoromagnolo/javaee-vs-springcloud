package jeevsspring.wildfly.poker.manager.engine.table;

import java.util.ArrayList;
import java.util.List;

public class Seats {

    private List<String> seats;

    public Seats(int numberOfSeats) {
        seats = new ArrayList<>(numberOfSeats);
    }

    public String get(int index) {
        return seats.get(index);
    }

    public void set(int index, String playerId) {
        seats.add(index, playerId);
    }

    public void unset(int index) {
        seats.add(index, null);
    }

    public int size() {
        return seats.size();
    }

    public String[] toArray() {
        return seats.toArray(new String[seats.size()]);
    }
}
