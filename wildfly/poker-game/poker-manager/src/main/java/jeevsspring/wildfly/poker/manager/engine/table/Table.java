package jeevsspring.wildfly.poker.manager.engine.table;

import jeevsspring.wildfly.poker.manager.engine.game.TexasHoldem;
import jeevsspring.wildfly.poker.manager.engine.player.Player;

/**
 * @author Marco Romagnolo
 */
public class Table {

    private final String id;
    private final String name;
    private final TableSettings settings;
    private final Player[] players;
    private final TexasHoldem game;
    private boolean published;
    private TableAction action;


    public Table(String id, String name, TableSettings settings) {
        this.id = id;
        this.name = name;
        this.settings = settings;
        this.players = new Player[settings.getNumberOfSeats()];
        this.game = new TexasHoldem();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TableSettings getSettings() {
        return settings;
    }

    public Player[] getPlayers() {
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
}
