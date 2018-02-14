package jeevsspring.wildfly.poker.manager.engine.hand;

import jeevsspring.wildfly.poker.manager.engine.player.Player;
import jeevsspring.wildfly.poker.manager.engine.table.Table;

import java.util.*;

/**
 * @author Marco Romagnolo
 */
public class Hand {

    private String id;
    private CardDeck cardDeck;
    private Table table;
    private long actionTimeOut;
    private Map<String, Player> players;
    private List<HandAction> actions;
    private long minBet;

    public Hand(Table table) {
        this.id = UUID.randomUUID().toString();
        this.cardDeck = new CardDeck();
        this.table = table;
        this.actionTimeOut = table.getSettings().getActionTimeOut();
        this.players = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public HandAction addAction(String playerId, HandActionType actionType) {
        Player player = players.get(playerId);
        HandAction action = new HandAction(table, player, actionType);
        actions.add(action);
        return action;
    }

    public HandAction addAction(String playerId, HandActionType actionType, long amount) {
        Player player = players.get(playerId);
        HandAction action = new HandAction(table, player, actionType, amount);
        actions.add(action);
        minBet = amount;
        return action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hand hand = (Hand) o;
        return Objects.equals(id, hand.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
