package jeevsspring.wildfly.poker.manager.engine.hand;

import jeevsspring.wildfly.poker.manager.engine.table.Table;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marco Romagnolo
 */
@Stateful
@LocalBean
public class Hands {

    private Map<String, Hand> hands;

    @PostConstruct
    public void init() {
        hands = new HashMap<>();
    }

    public Hand create(Table table) {
        Hand hand = new Hand(table);
        hands.put(hand.getId(), hand);
        return hand;
    }

    public void action(String handId, String playerId, HandActionType actionType) {
        Hand hand = hands.get(handId);
        hand.addAction(playerId, actionType);
    }

    public void action(String handId, String playerId, HandActionType actionType, long amount) {
        Hand hand = hands.get(handId);
        hand.addAction(playerId, actionType, amount);
    }

}
