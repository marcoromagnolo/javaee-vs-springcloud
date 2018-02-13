package jeevsspring.wildfly.poker.manager.engine.player;

import jeevsspring.wildfly.poker.manager.engine.hand.Card;
import jeevsspring.wildfly.poker.manager.engine.hand.HandAction;

import java.util.List;

/**
 * @author Marco Romagnolo
 */
public class Player {

    private final String id;
    private final String nickname;
    private long balance;
    private List<Card> cards;
    private HandAction action;
    private boolean sitOut;
    private boolean turn;
    private boolean dealer;
    private boolean smallBlind;
    private boolean bigBlind;

    public Player(String id, String nickname, long balance) {
        this.id = id;
        this.nickname = nickname;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public HandAction getAction() {
        return action;
    }

    public void setAction(HandAction action) {
        this.action = action;
    }

    public boolean isSitOut() {
        return sitOut;
    }

    public void setSitOut(boolean sitOut) {
        this.sitOut = sitOut;
    }

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public boolean isDealer() {
        return dealer;
    }

    public void setDealer(boolean dealer) {
        this.dealer = dealer;
    }

    public boolean isSmallBlind() {
        return smallBlind;
    }

    public void setSmallBlind(boolean smallBlind) {
        this.smallBlind = smallBlind;
    }

    public boolean isBigBlind() {
        return bigBlind;
    }

    public void setBigBlind(boolean bigBlind) {
        this.bigBlind = bigBlind;
    }

    public HandAction actionListen(long actionTimeOut) {

    }
}
