package jeevsspring.wildfly.poker.manager.engine.hand;

import jeevsspring.wildfly.poker.manager.engine.player.Player;
import jeevsspring.wildfly.poker.manager.engine.player.PlayerAction;
import jeevsspring.wildfly.poker.manager.engine.player.PlayerActionType;
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
    private List<PlayerAction> actions;

    public Hand(Table table) {
        this.id = UUID.randomUUID().toString();
        this.cardDeck = new CardDeck();
        this.table = table;
        this.actionTimeOut = table.getSettings().getActionTimeOut();
        this.players = new HashMap<>();
        this.actions = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void addAction(String playerId, PlayerActionType actionType, Long amount) {
        Player player = players.get(playerId);
        PlayerAction action = new PlayerAction(table, player, actionType, amount);
        actions.add(action);
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
