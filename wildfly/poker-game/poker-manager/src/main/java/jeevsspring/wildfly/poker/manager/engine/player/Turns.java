package jeevsspring.wildfly.poker.manager.engine.player;

import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marco Romagnolo
 */
public class Turns {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    // {index: seat} turns = {2, 3, 7}
    private List<Integer> list;

    // Current index
    private int index;

    public Turns(List<Integer> orderList) {
        this(orderList, 0);
    }

    public Turns(List<Integer> orderList, int index) {
        logger.debug("Turns :: Constructor(" + orderList + ", " + index + ")");

        this.list = new ArrayList<>();
        list.addAll(orderList);
    }

    public Integer indexOf(Integer turn) {
        return list.indexOf(turn);
    }

    public int current() {
        return index;
    }

    public void set(int index) {
        this.index = index;
    }

    public void remove(int index) {
        list.remove(index);
    }

    public void next() {
        index = (index + 1) % list.size();
    }
}
