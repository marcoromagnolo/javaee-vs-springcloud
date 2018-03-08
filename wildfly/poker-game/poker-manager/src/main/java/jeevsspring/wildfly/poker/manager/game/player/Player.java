package jeevsspring.wildfly.poker.manager.game.player;

import jeevsspring.wildfly.poker.manager.game.hand.Card;
import org.jboss.logging.Logger;

import java.util.List;
import java.util.Objects;

/**
 * @author Marco Romagnolo
 */
public class Player {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    private final String id;
    private final String nickname;
    private long balance;
    private int seat;
    private List<Card> cards;
    private boolean sitOut;

    public Player(String id, String nickname, long balance) {
        logger.debug("Player(" + id + ", " + nickname + ", " + balance + ")");
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

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
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

    public boolean isSitOut() {
        return sitOut;
    }

    public void setSitOut(boolean sitOut) {
        this.sitOut = sitOut;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
