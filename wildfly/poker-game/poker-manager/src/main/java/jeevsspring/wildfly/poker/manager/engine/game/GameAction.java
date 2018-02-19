package jeevsspring.wildfly.poker.manager.engine.game;

import jeevsspring.wildfly.poker.common.GameType;
import jeevsspring.wildfly.poker.manager.engine.hand.Card;
import jeevsspring.wildfly.poker.manager.engine.hand.CardDeck;
import jeevsspring.wildfly.poker.manager.engine.hand.Pot;
import jeevsspring.wildfly.poker.manager.engine.player.Player;

import java.util.List;
import java.util.Map;

/**
 * @author Marco Romagnolo
 */
public class GameAction {

    private long id;
    private String handId;
    private String tableId;
    private CardDeck cardDeck;
    private Map<String, String> visitors;
    private Map<String, Player> players;
    private String[] seats;
    private GameType game;
    private List<Card> communityCards;
    private List<Pot> pots;
    private String turn;

    public GameAction(String handId, String tableId) {
        this.id = System.nanoTime();
        this.handId = handId;
        this.tableId = tableId;
    }

    public long getId() {
        return id;
    }

    public String getHandId() {
        return handId;
    }

    public String getTableId() {
        return tableId;
    }

    public CardDeck getCardDeck() {
        return cardDeck;
    }

    public void setCardDeck(CardDeck cardDeck) {
        this.cardDeck = cardDeck;
    }

    public Map<String, String> getVisitors() {
        return visitors;
    }

    public void setVisitors(Map<String, String> visitors) {
        this.visitors = visitors;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public void setPlayers(Map<String, Player> players) {
        this.players = players;
    }

    public String[] getSeats() {
        return seats;
    }

    public void setSeats(String[] seats) {
        this.seats = seats;
    }

    public GameType getGame() {
        return game;
    }

    public void setGame(GameType game) {
        this.game = game;
    }

    public List<Card> getCommunityCards() {
        return communityCards;
    }

    public void setCommunityCards(List<Card> communityCards) {
        this.communityCards = communityCards;
    }

    public List<Pot> getPots() {
        return pots;
    }

    public void setPots(List<Pot> pots) {
        this.pots = pots;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }
}
