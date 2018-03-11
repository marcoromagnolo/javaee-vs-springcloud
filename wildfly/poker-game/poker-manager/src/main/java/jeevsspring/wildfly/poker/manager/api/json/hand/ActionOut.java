package jeevsspring.wildfly.poker.manager.api.json.hand;

import jeevsspring.wildfly.poker.manager.api.json.hand.CardOut;
import jeevsspring.wildfly.poker.manager.api.json.hand.PlayerOut;
import jeevsspring.wildfly.poker.manager.api.json.hand.PotOut;

import java.util.List;

/**
 * @author Marco Romagnolo
 */
public class ActionOut {

    private long actionId;
    private String handId;
    private String tableId;
    private List<String> visitors;
    private List<PlayerOut> players;
    private String[] seats;
    private List<CardOut> communityCards;
    private List<PotOut> pots;
    private String turn;

    public long getActionId() {
        return actionId;
    }

    public void setActionId(long actionId) {
        this.actionId = actionId;
    }

    public String getHandId() {
        return handId;
    }

    public void setHandId(String handId) {
        this.handId = handId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public List<String> getVisitors() {
        return visitors;
    }

    public void setVisitors(List<String> visitors) {
        this.visitors = visitors;
    }

    public List<PlayerOut> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerOut> players) {
        this.players = players;
    }

    public String[] getSeats() {
        return seats;
    }

    public void setSeats(String[] seats) {
        this.seats = seats;
    }

    public List<CardOut> getCommunityCards() {
        return communityCards;
    }

    public void setCommunityCards(List<CardOut> communityCards) {
        this.communityCards = communityCards;
    }

    public List<PotOut> getPots() {
        return pots;
    }

    public void setPots(List<PotOut> pots) {
        this.pots = pots;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }
}
