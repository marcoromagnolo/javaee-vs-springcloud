package jeevsspring.wildfly.poker.manager.engine.player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marco Romagnolo
 */
public class Turns {

    // {turn: index} turns = {3, 6, 9}
    private List<Integer> turns;
    private int turn;

    public Turns(List<Integer> indexes) {
        this.turns = new ArrayList<>();
        for (int index : indexes) {
            turns.add(index);
        }
    }

    public Integer getTurnByOrderIndex(int index) {
        for (int turn = 0; turn < turns.size(); turn++) {
            if (turns.get(turn) == index) return turn;
        }
        return null;
    }

    public Integer get(int turn) {
        return turns.get(turn);
    }

    public int current() {
        return turn;
    }

    public void set(int turn) {
        this.turn = turn;
    }

    public void remove(int turn) {
        turns.remove(turn);
    }

    public void next() {
        turn = (turn + 1) % turns.size();
    }
}
