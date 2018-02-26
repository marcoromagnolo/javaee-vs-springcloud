package jeevsspring.wildfly.poker.manager.engine.game;

import jeevsspring.wildfly.poker.common.GameType;
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
    private String tableName;
    private int numberOfSeats;
    private long actionTimeout;
    private long startTimeout;
    private GameType gameType;

    // Back-Office Client
    private final BoClient boClient;

    // List of game actions result to return to players
    private Queue<E> queue;

    private Map<String, HandAction> temp;

    // True when hand is running
    private boolean handRunning;

    // True when round is running
    private boolean roundRunning;

    // True if game is running playing
    private boolean running;

    // Game will start when start is over
    private boolean starting;

    // True if game can be started
    private boolean startable;

    // Settings to update for this game
    private TableSettings updateSettings;

    // Time of start
    private Long startTime;

    // Time of stop
    private Long stopTime;

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

    // index of the next player on hand
    private int turn;

    // index of the player dealer
    private int dealer;

    public Game(String tableId, TableSettings settings, BoClient boClient) {
        this.tableId = tableId;
        setSettings(settings);
        this.boClient = boClient;
        this.players = new HashMap<>();
        this.visitors = new ArrayList<>();
        this.cardDeck = new CardDeck();
        this.seats = new Seats(numberOfSeats);
        this.communityCards = new ArrayList<>();
        this.pots = new ArrayList<>();
    }

    public abstract void action(HandAction action);

    public abstract void action(TableAction action);

    public void setSettings(TableSettings settings) {
        this.tableName = settings.getName();
        this.numberOfSeats = settings.getNumberOfSeats();
        this.actionTimeout = settings.getActionTimeOut();
        this.startTimeout = settings.getStartTimeout();
    }

    public String getTableId() {
        return tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public BoClient getBoClient() {
        return boClient;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public long getActionTimeout() {
        return actionTimeout;
    }

    public long getStartTimeout() {
        return startTimeout;
    }

    public GameType getGameType() {
        return gameType;
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

    public boolean isStarting() {
        return starting;
    }

    protected void setStarting(boolean starting) {
        this.starting = starting;
    }

    public boolean isStartable() {
        return startable;
    }

    public void setStartable(boolean startable) {
        this.startable = startable;
    }

    public void start() {
        startTime = System.currentTimeMillis();
        startable = false;
    }

    public boolean isStarted() {
        return startTime != null;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void waitStart() {
        long now;
        do {
            now = System.currentTimeMillis();
            starting = true;
        } while (now < startTime + startTimeout);
        starting = false;
    }

    public void update(TableSettings settings) {
        updateSettings = settings;
    }

    public void stop() {
        stopTime = System.currentTimeMillis();
    }

    public Long getStopTime() {
        return stopTime;
    }

    public void delete() {
        stop();
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

    public int getTurn() {
        return turn;
    }

    protected void setTurn(int turn) {
        this.turn = turn;
    }

    public int getDealer() {
        return dealer;
    }

    protected void setDealer(int dealer) {
        this.dealer = dealer;
    }

    protected void calculatePots() {

    }

    protected void reward() {

    }
}