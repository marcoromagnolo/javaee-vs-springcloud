package jeevsspring.wildfly.poker.manager.engine.game;

import jeevsspring.wildfly.poker.common.TableSettings;
import jeevsspring.wildfly.poker.manager.bo.BoClient;
import jeevsspring.wildfly.poker.manager.engine.hand.Card;
import jeevsspring.wildfly.poker.manager.engine.hand.CardDeck;
import jeevsspring.wildfly.poker.manager.engine.hand.HandAction;
import jeevsspring.wildfly.poker.manager.engine.hand.Pot;
import jeevsspring.wildfly.poker.manager.engine.player.Player;
import jeevsspring.wildfly.poker.manager.engine.table.Seats;
import jeevsspring.wildfly.poker.manager.engine.table.TableAction;

import java.util.*;

public abstract class Game<E extends GameAction> {

    // Table Id
    private final String tableId;

    private String handId;

    // Table Settings
    private TableSettings settings;

    // Table Settings to update
    private TableSettings updateSettings;

    // Back-Office Client
    private final BoClient boClient;

    // List of game actions result to return to players
    private Queue<E> queue;

    // True when hand is running
    private boolean handRunning;

    // True when round is running
    private boolean roundRunning;

    // True if game is running playing
    private boolean running;

    // Time of start
    private Long startTime;

    // List of Players
    private Map<String, Player> players;

    // List of player visitors (don't play)
    private List<String> visitors;

    // Card Deck
    private CardDeck cardDeck;

    // Seats associated to playerId (array index is the seat number)
    private Seats seats;

    // Community Cards
    private List<Card> communityCards;

    // List of Pots
    private List<Pot> pots;

    // index of the player dealer
    private int dealer;

    public Game(String tableId, TableSettings settings, BoClient boClient) {
        this.tableId = tableId;
        this.settings = settings;
        this.boClient = boClient;
        this.players = new HashMap<>();
        this.visitors = new ArrayList<>();
        this.communityCards = new ArrayList<>();
        this.pots = new ArrayList<>();
        this.startTime = System.currentTimeMillis();
    }

    public void onSettingsApply() {
        this.cardDeck = new CardDeck();
        this.seats = new Seats(settings.getNumberOfSeats());
    }

    public void doActions(Map<String, HandAction> handActions, Queue<TableAction> tableActions) {

        // Hand action
        onAction(handActions);

        // Table action
        while (tableActions.peek() != null) {
            TableAction action = tableActions.poll();
            onAction(action);
        }
    }

    protected abstract void onAction(Map<String, HandAction> actions);

    protected abstract void onAction(TableAction action);

    public String getTableId() {
        return tableId;
    }

    public BoClient getBoClient() {
        return boClient;
    }

    public TableSettings getSettings() {
        return settings;
    }

    public String getHandId() {
        return handId;
    }

    protected void setHandId(String handId) {
        this.handId = handId;
    }

    public Queue<E> getQueue() {
        return queue;
    }

    public boolean isHandRunning() {
        return handRunning;
    }

    protected void setHandRunning(boolean handRunning) {
        this.handRunning = handRunning;
    }

    public boolean isRoundRunning() {
        return roundRunning;
    }

    protected void setRoundRunning(boolean roundRunning) {
        this.roundRunning = roundRunning;
    }

    public boolean isRunning() {
        return running;
    }

    protected void setRunning(boolean running) {
        this.running = running;
    }

    public void update(TableSettings settings) {
        updateSettings = settings;
    }

    public void delete() {

    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public List<String> getVisitors() {
        return visitors;
    }

    public CardDeck getCardDeck() {
        return cardDeck;
    }

    public Seats getSeats() {
        return seats;
    }

    public List<Card> getCommunityCards() {
        return communityCards;
    }

    public List<Pot> getPots() {
        return pots;
    }

    public void setPots(List<Pot> pots) {
        this.pots = pots;
    }

    public int getDealer() {
        return dealer;
    }

    protected void setDealer(int dealer) {
        this.dealer = dealer;
    }
}
