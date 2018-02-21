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

    // True when hand is finished
    private boolean handFinished;

    // Players playing
    private boolean playing;

    // Game will start when startTimer is over
    private boolean starting;

    // Settings to update for this game
    private TableSettings updateSettings;

    // True if game will stop
    private boolean stop;

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

    // PlayerId of the next player turn
    private String turn;

    private boolean dealer;

    private long start;

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

    public long getStartTimout() {
        return startTimeout;
    }

    public Queue<GameAction> getQueue() {
        return queue;
    }

    public abstract void action(HandAction action);

    public abstract void action(TableAction action);

    public boolean isHandFinished() {
        return handFinished;
    }

    protected void setHandFinished(boolean handFinished) {
        this.handFinished = handFinished;
    }

    public boolean isPlaying() {
        return playing;
    }

    protected void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public boolean isStarting() {
        return starting;
    }

    public void setStarting(boolean starting) {
        this.starting = starting;
    }

    public void start() {
        start = System.currentTimeMillis();
    }

    public void waitStart() {
        long now;
        do {
            now = System.currentTimeMillis();
        } while(now < start + startTimeout);
        starting = false;
        playing = true;
    }

    public void update(TableSettings settings) {
        updateSettings = settings;
    }

    public void stop() {
        stop = true;
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

    public String getTurn() {
        return turn;
    }

    public boolean isDealer() {
        return dealer;
    }
}
