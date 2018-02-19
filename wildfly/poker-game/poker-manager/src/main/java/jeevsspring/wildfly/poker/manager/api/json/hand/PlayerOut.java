package jeevsspring.wildfly.poker.manager.api.json.hand;

import java.util.List;

/**
 * @author Marco Romagnolo
 */
public class PlayerOut {

    private String nickname;
    private long balance;
    private int seat;
    private List<CardOut> cards;
    private boolean sitOut;
    private boolean turn;
    private boolean dealer;
    private boolean smallBlind;
    private boolean bigBlind;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public List<CardOut> getCards() {
        return cards;
    }

    public void setCards(List<CardOut> cards) {
        this.cards = cards;
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
}
