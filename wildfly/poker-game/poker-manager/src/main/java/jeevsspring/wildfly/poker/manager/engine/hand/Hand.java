package jeevsspring.wildfly.poker.manager.engine.hand;

import jeevsspring.wildfly.poker.manager.engine.player.Player;
import jeevsspring.wildfly.poker.manager.engine.table.Table;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marco Romagnolo
 */
public class Hand {

    private CardDeck cardDeck;
    private Table table;
    private long actionTimeOut;
    private Map<Player, HandAction> actions;

    public Hand(Table table) {
        this.cardDeck = new CardDeck();
        this.table = table;
        this.actionTimeOut = table.getSettings().getActionTimeOut();
        this.actions = new HashMap<>();
    }

    public void start() {
        for (Player player : table.getPlayers()) {
            actions.put(player, player.actionListen(actionTimeOut));
        }
    }
}
