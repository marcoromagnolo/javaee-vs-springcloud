package jeevsspring.wildfly.poker.manager.game.hand;

import org.jboss.logging.Logger;

import java.util.Comparator;
import java.util.Objects;

/**
 * @author Marco Romagnolo
 */
public class Card implements Comparator<Card>{

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    private boolean shadow;
    private CardSuitType suit;
    private CardSymbolType symbol;
    private CardColorType color;

    public Card() {
        this.shadow = true;
    }

    Card(CardSuitType suit, CardSymbolType symbol) {
        logger.debug("Card(" + suit + ", " + symbol + ")");
        if (suit == null || symbol == null) {
            this.suit = null;
            this.symbol = null;
            this.color = null;
            this.shadow = true;
        } else {
            if (suit.equals(CardSuitType.HEARTS) || suit.equals(CardSuitType.DIAMONDS)) {
                this.color = CardColorType.RED;
            } else {
                this.color = CardColorType.BLACK;
            }
            this.suit = suit;
            this.symbol = symbol;
        }
    }

    public boolean isShadow() {
        return shadow;
    }

    public CardSuitType getSuit() {
        return suit;
    }

    public CardSymbolType getSymbol() {
        return symbol;
    }

    public CardColorType getColor() {
        return color;
    }

    @Override
    public int compare(Card o1, Card o2) {
        logger.debug("compare(" + o1 + ", " + o2 + ")");
        if (shadow) return 0;
        int suitRank = o1.getSuit().getValue().compareTo(o2.getSuit().getValue());
        if (suitRank == 0) {
            return o1.getSymbol().getValue().compareTo(o2.getSymbol().getValue());
        }
        return suitRank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return shadow == card.shadow &&
                suit == card.suit &&
                symbol == card.symbol &&
                color == card.color;
    }

    @Override
    public int hashCode() {

        return Objects.hash(shadow, suit, symbol, color);
    }
}
