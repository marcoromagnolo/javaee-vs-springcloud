package jeevsspring.wildfly.poker.manager.engine.game;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marco Romagnolo
 */
@Stateful
@LocalBean
public class GameActions {

    private Map<String, List<GameAction>> actions;

    public GameActions() {
        this.actions = new HashMap<>();
    }

    public Map<String, List<GameAction>> getActions() {
        return actions;
    }
}
