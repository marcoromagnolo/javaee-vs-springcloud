package jeevsspring.wildfly.poker.manager.game.hand;

import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.List;

public class Pots {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    // List of Side Pots
    private List<Pot> side = new ArrayList<>();

    // Main Pot
    private Pot main;

    public Pot getMain() {
        return main;
    }

    public List<Pot> getSide() {
        return side;
    }

    @Override
    public String toString() {
        return "Pots{" +
                "side=" + side +
                ", main=" + main +
                '}';
    }
}
