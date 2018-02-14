package jeevsspring.wildfly.poker.manager.engine.hand;

import jeevsspring.wildfly.poker.manager.engine.player.PlayerActionType;
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

    public Hand get(String handId) {
        Hand hand = hands.get(handId);
        return hand;
    }

    public void addHandAction(String handId, String playerId, PlayerActionType actionType, Long amount) {
        Hand hand = get(handId);
        hand.addAction(playerId, actionType, amount);
    }

    public void addHandAction(String handId, String playerId, PlayerActionType actionType) {
        addHandAction(handId, playerId, actionType);
    }
}
