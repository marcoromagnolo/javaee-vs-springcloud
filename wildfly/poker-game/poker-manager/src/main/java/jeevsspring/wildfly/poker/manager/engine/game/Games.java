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
public class Games<E extends Game> {

    private Map<String, E> instances;

    @PostConstruct
    public void init() {
        instances = new HashMap<>();
    }

    public void add(E game) {
        instances.put(game.getTableId(), game);
    }

    public Collection<E> getAll() {
        return instances.values();
    }


    public E get(String tableId) {
        return instances.get(tableId);
    }
}