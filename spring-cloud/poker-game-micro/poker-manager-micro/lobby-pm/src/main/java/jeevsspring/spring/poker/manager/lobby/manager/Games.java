package jeevsspring.spring.poker.manager.lobby.manager;

import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marco Romagnolo
 */
@Service
public class Games {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    private Map<String, Game> instances;

    @PostConstruct
    public void init() {
        instances = new HashMap<>();
    }

    public void add(Game game) {
        instances.put(game.getTableId(), game);
    }

    public void remove(String tableId) throws GameException {
        if (!instances.containsKey(tableId)) throw new GameException(ErrorCode.GAME_NOT_FOUND, "This table doesn't exists: " + tableId);;
        instances.remove(tableId);
    }

    public List<Game> getAll() {
        return new ArrayList<>(instances.values());
    }


    public Game get(String tableId) throws GameException {
        if (!instances.containsKey(tableId)) throw new GameException(ErrorCode.GAME_NOT_FOUND, "This table doesn't exists: " + tableId);
        return instances.get(tableId);
    }
}
