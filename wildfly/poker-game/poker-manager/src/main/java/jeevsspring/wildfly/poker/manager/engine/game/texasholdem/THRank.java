package jeevsspring.wildfly.poker.manager.engine.game.texasholdem;

import jeevsspring.wildfly.poker.manager.engine.hand.Card;
import org.jboss.logging.Logger;

import java.util.List;

public class THRank implements Comparable {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    private List<Card> cards;

    private Rank rank;

    /**
     * Constructor
     * @param cards
     */
    public THRank(List<Card> cards) {
        this.cards = cards;
        calculate();
    }

    @Override
    public int compareTo(Object o) {
        THRank rank = (THRank) o;
        int compare = Integer.compare(this.score(), rank.score());
        if (compare == 0) {
            return 0;
        }
        return compare;
    }

    public int score() {
        return rank.score();
    }

    public Rank rank() {
        return rank;
    }

    private void calculate() {
        for (Card card : cards) {

        }
    }

    /**
     * Texas Holdem Ranks
     */
    public enum Rank {
        HIGH_CARD(0), ONE_PAIR(1), TWO_PAIR(2), THREE_OF_A_KIND(3), STRAIGHT(4),
        FLUSH(5), FULL_HOUSE(6), FOUR_OF_A_KIND(7), STRAIGHT_FLUSH(8), ROYAL_FLUSH(9);

        private final int rank;

        Rank(int i) {
            rank = i;
        }

        public int score() {
            return rank;
        }
    }



}
