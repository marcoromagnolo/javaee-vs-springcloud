package jeevsspring.wildfly.poker.manager.engine.game;

import jeevsspring.wildfly.poker.common.TableSettings;
import jeevsspring.wildfly.poker.manager.bo.BoClient;
import jeevsspring.wildfly.poker.manager.engine.hand.Card;
import jeevsspring.wildfly.poker.manager.engine.hand.CardDeck;
import jeevsspring.wildfly.poker.manager.engine.hand.HandAction;
import jeevsspring.wildfly.poker.manager.engine.hand.Pots;
import jeevsspring.wildfly.poker.manager.engine.player.Player;
import jeevsspring.wildfly.poker.manager.engine.table.Seats;
import jeevsspring.wildfly.poker.manager.engine.table.TableAction;
import org.jboss.logging.Logger;

import java.util.*;

public abstract class Game<E extends GameAction> {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

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
    private Pots pots;

    // index of the player dealer
    private int dealer;

    public Game(String tableId, TableSettings settings, BoClient boClient) {
        logger.debug("THGame :: Constructor(" + tableId + ", " + settings + ", " + boClient + ")");

        this.tableId = tableId;
        this.settings = settings;
        this.boClient = boClient;
        this.players = new HashMap<>();
        this.visitors = new ArrayList<>();
        this.communityCards = new ArrayList<>();
        this.pots = new Pots();
        this.startTime = System.currentTimeMillis();
    }

    public void onSettingsApply() {
        logger.debug("THGame :: onSettingsApply()");

        this.cardDeck = new CardDeck();
        this.seats = new Seats(settings.getNumberOfSeats());
    }

    public void doActions(Map<String, HandAction> handActions, Queue<TableAction> tableActions) {
        logger.debug("THGame :: doActions(" + handActions + ", " + tableActions + ")");

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
        logger.debug("THGame :: update(" + settings + ")");
        updateSettings = settings;
    }

    public void delete() {
        logger.debug("THGame :: delete()");
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public Player getPlayer(String playerId) {
        return players.get(playerId);
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

    public Pots getPots() {
        return pots;
    }

    public void setPots(Pots pots) {
        this.pots = pots;
    }

    public int getDealer() {
        return dealer;
    }

    protected void setDealer(int dealer) {
        this.dealer = dealer;
    }
}
