package jeevsspring.wildfly.poker.manager.engine.hand;

import jeevsspring.wildfly.poker.manager.engine.game.Game;
import jeevsspring.wildfly.poker.manager.engine.game.Games;
import jeevsspring.wildfly.poker.manager.exception.PMException;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author Marco Romagnolo
 */
@Singleton
@LocalBean
public class HandActionQueue {

    @EJB
    private Games games;

    private Queue<HandAction> actions;

    @PostConstruct
    public void init() {
        actions = new ArrayDeque<>();
    }

    public boolean isEmpty() {
        return actions.isEmpty();
    }

    public boolean insert(HandActionType actionType, String tableId, String handId, String playerId, String option) throws PMException {
        Game game = games.get(tableId);
        if (!game.isRunning()) throw new PMException();
        HandAction hand = new HandAction(actionType, tableId, handId, playerId, option);
        return actions.offer(hand);
    }

    public HandAction examine() {
        return actions.peek();
    }

    public HandAction poll() {
        return actions.poll();
    }

}
