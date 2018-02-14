package jeevsspring.wildfly.poker.manager.engine.table;

import jeevsspring.wildfly.poker.manager.engine.game.GameType;
import jeevsspring.wildfly.poker.manager.engine.hand.Card;
import jeevsspring.wildfly.poker.manager.engine.player.Player;
import jeevsspring.wildfly.poker.manager.util.IdGenerator;

import java.util.*;

/**
 * @author Marco Romagnolo
 */
public class Table {

    private final String id;
    private final TableSettings settings;
    private final List<Player> players;
    private final String[] seats;
    private final GameType game;
    private boolean published;
    private List<Card> communityCards;
    private List<Pot> pots;
    private Map<String, Pot> playerPot;
    private List<TableAction> actions;

    public Table(TableSettings settings) {
        this.id = IdGenerator.newTableId();
        this.settings = settings;
        this.players = new ArrayList<>();
        this.seats = new String[settings.getNumberOfSeats()];
        this.game = GameType.TEXAS_HOLDEM;
        this.communityCards = new ArrayList<>();
        this.pots = new ArrayList<>();
        this.playerPot = new HashMap<>();
        this.published = true;
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

    public GameType getGame() {
        return game;
    }

    public List<Card> getCommunityCards() {
        return communityCards;
    }

    public List<Pot> getPots() {
        return pots;
    }

    public Map<String, Pot> getPlayerPot() {
        return playerPot;
    }

    public void addAction(TableActionType actionType, String playerId) {
        actions.add(new TableAction(actionType, playerId));
    }

    public void addAction(TableActionType actionType, String playerId, long amount) {
        actions.add(new TableAction(actionType, playerId, amount));
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
