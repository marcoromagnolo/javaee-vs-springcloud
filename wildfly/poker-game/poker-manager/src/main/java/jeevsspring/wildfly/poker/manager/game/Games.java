package jeevsspring.wildfly.poker.manager.game;

import jeevsspring.wildfly.poker.manager.game.engine.Game;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marco Romagnolo
 */
@Singleton
@LocalBean
public class Games<E extends Game> {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    private Map<String, E> instances;

    @PostConstruct
    public void init() {
        instances = new HashMap<>();
    }

    public void add(E game) {
        instances.put(game.getTableId(), game);
    }

    public void remove(String tableId) throws GameException {
        if (!instances.containsKey(tableId)) throw new GameException("This table doesn't exists: " + tableId);;
        instances.remove(tableId);
    }

    public List<E> getAll() {
        return new ArrayList<>(instances.values());
    }


    public E get(String tableId) throws GameException {
        if (!instances.containsKey(tableId)) throw new GameException("This table doesn't exists: " + tableId);
        return instances.get(tableId);
    }
}
