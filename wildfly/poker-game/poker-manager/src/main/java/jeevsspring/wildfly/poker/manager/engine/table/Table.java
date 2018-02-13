package jeevsspring.wildfly.poker.manager.engine.table;

import jeevsspring.wildfly.poker.manager.engine.game.TexasHoldem;
import jeevsspring.wildfly.poker.manager.engine.hand.Card;
import jeevsspring.wildfly.poker.manager.engine.player.Player;

import java.util.*;

/**
 * @author Marco Romagnolo
 */
public class Table {

    private final String id;
    private final TableSettings settings;
    private final List<Player> players;
    private final String[] seats;
    private final TexasHoldem game;
    private boolean published;
    private List<Card> cards;
    private List<Pot> pots;
    private Map<String, Pot> playerPot;
    private TableAction action;

    public Table(String id, TableSettings settings) {
        this.id = id;
        this.settings = settings;
        this.players = new ArrayList<>();
        this.seats = new String[settings.getNumberOfSeats()];
        this.game = new TexasHoldem();
        this.cards = new ArrayList<>();
        this.pots = new ArrayList<>();
        this.playerPot = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public String[] getSeats() {
        return seats;
    }

    public TableSettings getSettings() {
        return settings;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public TableActionType getAction() {
        return action.getType();
    }

    public void setAction(TableActionType actionType) {
        this.action = new TableAction(actionType);
    }

    public TexasHoldem getGame() {
        return game;
    }

    public List<Card> getCards() {
        return cards;
    }

    public List<Pot> getPots() {
        return pots;
    }

    public Map<String, Pot> getPlayerPot() {
        return playerPot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return Objects.equals(id, table.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
