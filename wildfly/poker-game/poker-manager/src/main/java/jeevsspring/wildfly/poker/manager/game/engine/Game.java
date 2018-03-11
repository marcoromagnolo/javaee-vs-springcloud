package jeevsspring.wildfly.poker.manager.game.engine;

import jeevsspring.wildfly.poker.common.TableSettings;
import jeevsspring.wildfly.poker.manager.game.hand.Card;
import jeevsspring.wildfly.poker.manager.game.hand.CardDeck;
import jeevsspring.wildfly.poker.manager.game.hand.HandAction;
import jeevsspring.wildfly.poker.manager.game.hand.Pots;
import jeevsspring.wildfly.poker.manager.game.player.Player;
import jeevsspring.wildfly.poker.manager.game.table.Seats;
import jeevsspring.wildfly.poker.manager.game.table.TableAction;
import jeevsspring.wildfly.poker.manager.util.IdGenerator;
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

    // List of game actions result to return to players
    private Queue<E> gameActions;

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

    private Map<String, Long> rewards;

    public Game(TableSettings settings) {
        logger.debug("Game(" + settings + ")");

        this.tableId = IdGenerator.newTableId();
        this.settings = settings;
        this.players = new HashMap<>();
        this.visitors = new ArrayList<>();
        this.communityCards = new ArrayList<>();
        this.pots = new Pots();
        this.seats = new Seats(settings.getNumberOfSeats());
        this.startTime = System.currentTimeMillis();
        logger.debug("Game() apply settings");
        onSettingsApply();
    }

    public abstract void onSettingsApply();

    public abstract void actions(Map<String, HandAction> actions);

    public abstract void actions(Queue<TableAction> action);

    public String getTableId() {
        return tableId;
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

    public Queue<E> getGameActions() {
        return gameActions;
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
        logger.debug("update(" + settings + ")");
        updateSettings = settings;
    }

    public void delete() {
        logger.debug("delete()");
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
        logger.debug("getCardDeck()");
        if (cardDeck == null) {
            this.cardDeck = new CardDeck();
        }
        return cardDeck;
    }

    public Seats getSeats() {
        logger.debug("getSeats()");
        if (seats == null) {
            new Seats(settings.getNumberOfSeats());
        }
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

    public Map<String, Long> getRewards() {
        if (rewards == null) {
            rewards = new HashMap<>();
        }
        return rewards;
    }

    @Override
    public String toString() {
        return "Game{" +
                "tableId='" + tableId + '\'' +
                ", handId='" + handId + '\'' +
                ", settings=" + settings +
                ", updateSettings=" + updateSettings +
                ", queue=" + gameActions +
                ", handRunning=" + handRunning +
                ", roundRunning=" + roundRunning +
                ", running=" + running +
                ", startTime=" + startTime +
                ", players=" + players +
                ", visitors=" + visitors +
                ", cardDeck=" + cardDeck +
                ", seats=" + seats +
                ", communityCards=" + communityCards +
                ", pots=" + pots +
                ", dealer=" + dealer +
                '}';
    }

}
