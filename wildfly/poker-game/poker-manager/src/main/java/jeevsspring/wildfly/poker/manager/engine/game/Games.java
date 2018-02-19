package jeevsspring.wildfly.poker.manager.engine.game;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marco Romagnolo
 */
@Singleton
@LocalBean
public class Games {

    private Map<String, Game> instances;

    @PostConstruct
    public void init() {
        instances = new HashMap<>();
    }

    public void add(Game game) {
        instances.put(game.getTableId(), game);
    }

    public Collection<Game> getAll() {
        return instances.values();
    }


    public Game get(String tableId) {
        return instances.get(tableId);
    }
}
