package jeevsspring.wildfly.poker.manager.game.engine;

import jeevsspring.wildfly.poker.manager.game.GameException;
import jeevsspring.wildfly.poker.manager.game.hand.Card;
import jeevsspring.wildfly.poker.manager.game.player.Player;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import java.util.*;

/**
 * @author Marco Romagnolo
 */
@Singleton
@LocalBean
public class GameActions<E extends GameAction>  {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @EJB
    private Map<String, Queue<E>> actions;

    @PostConstruct
    public void init() {
        actions = new HashMap<>();
    }

    public void add(String tableId, Queue<E> gameAction) {
        actions.put(tableId, gameAction);
    }

    public Set<Map.Entry<String, Queue<E>>> getAll() {
        return actions.entrySet();
    }

    public List<E> get(String tableId, String playerId) throws GameException {
        if (!actions.containsKey(tableId)) throw new GameException("TableId not Found");
        List<E> list = new ArrayList<>();
        for (E action : actions.get(tableId)) {
            list.add(filter(action, playerId));
        }
        return list;
    }

    private E filter(E a, String playerId) {
        logger.trace("filter(" + a + ", " + playerId + ")");
        boolean isVisitor = a.getVisitors().contains(playerId);

        // Set Players
        for (Player player : a.getPlayers().values()) {

            Player p = new Player(player.getId(), player.getNickname(), player.getBalance());
            p.setSitOut(player.isSitOut());
            p.setSeat(player.getSeat());


            //Set Player Cards only for current player, only if isn't a visitor
            if (!playerId.equals(player.getId()) || isVisitor) {
                p.getCards().clear();
                for (int i =0 ; i < player.getCards().size(); i++) {
                    p.getCards().add(new Card());
                }
            }
        }

        return a;
    }
}
