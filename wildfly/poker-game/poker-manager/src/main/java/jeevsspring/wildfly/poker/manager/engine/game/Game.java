package jeevsspring.wildfly.poker.manager.engine.game;

import jeevsspring.wildfly.poker.common.TableSettings;
import jeevsspring.wildfly.poker.manager.engine.hand.Card;
import jeevsspring.wildfly.poker.manager.engine.hand.CardDeck;
import jeevsspring.wildfly.poker.manager.engine.hand.HandAction;
import jeevsspring.wildfly.poker.manager.engine.hand.Pot;
import jeevsspring.wildfly.poker.manager.engine.player.Player;
import jeevsspring.wildfly.poker.manager.engine.table.TableAction;

import java.util.*;

public abstract class Game {

    // Table Id
    private final String tableId;

    // Table Settings
    private String tableName;
    private int numberOfSeats;
    private long actionTimeout;
    private long startTimeout;

    // List of game actions result to return players
    private Queue<GameAction> queue;

    // True when hand is running
    private boolean handRunning;

    // True when round is running
    private boolean roundRunning;

    // True if game is running playing
    private boolean running;

    // Game will start when start is over
    private boolean starting;

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
    private String[] seats;

    // Community Cards
    private List<Card> communityCards;

    // List of Pots
    private List<Pot> pots;

    // index of the next player player on hand
    private int first;

    private int dealer;

    public Game(String tableId, TableSettings settings) {
        this.tableId = tableId;
        setSettings(settings);
        this.players = new HashMap<>();
        this.visitors = new ArrayList<>();
        this.cardDeck = new CardDeck();
        this.seats = new String[numberOfSeats];
        this.communityCards = new ArrayList<>();
        this.pots = new ArrayList<>();
    }

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

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public long getActionTimeout() {
        return actionTimeout;
    }

    public long getStartTimeout() {
        return startTimeout;
    }

    public Queue<GameAction> getQueue() {
        return queue;
    }

    protected void addGameAction(GameAction gameAction) {

    }

    public abstract void action(HandAction action);

    public abstract void action(TableAction action);

    public boolean isHandRunning() {
        return handRunning;
    }

    protected void setHandRunning(boolean handRunning) {
        this.handRunning = handRunning;
    }

    public boolean isRoundRunning() {
        return roundRunning;
    }

    public void setRoundRunning(boolean roundRunning) {
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

    public void setStarting(boolean starting) {
        this.starting = starting;
    }

    public void start() {
        startTime = System.currentTimeMillis();
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

    public Map<String, Player> getPlayers() {
        return players;
    }

    public List<String> getVisitors() {
        return visitors;
    }

    public CardDeck getCardDeck() {
        return cardDeck;
    }

    public String[] getSeats() {
        return seats;
    }

    public List<Card> getCommunityCards() {
        return communityCards;
    }

    public List<Pot> getPots() {
        return pots;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getDealer() {
        return dealer;
    }

    public void setDealer(int dealer) {
        this.dealer = dealer;
    }

    public int randomSeat() {
        Random r = new Random();
        return r.nextInt(numberOfSeats);
    }

    public int nextSeat(int seat) {
        if (seat < 0) seat = 0;
        return (seat + 1) % numberOfSeats;
    }
}
