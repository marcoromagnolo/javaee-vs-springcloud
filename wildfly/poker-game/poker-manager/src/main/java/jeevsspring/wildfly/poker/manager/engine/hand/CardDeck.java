package jeevsspring.wildfly.poker.manager.engine.hand;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

/**
 * @author Marco Romagnolo
 */
public class CardDeck {

    public static final int CARD_NUMBER = 52;
    public static final int SUIT_CARD_NUMBER = 13;

    private Stack<Card> cards;

    public CardDeck() {
        Set<Card> set = new HashSet<>();
        set.add(new Card(CardSuitType.SPADES, CardSymbolType.ACE));
        set.add(new Card(CardSuitType.SPADES, CardSymbolType.TWO));
        set.add(new Card(CardSuitType.SPADES, CardSymbolType.THREE));
        set.add(new Card(CardSuitType.SPADES, CardSymbolType.FOUR));
        set.add(new Card(CardSuitType.SPADES, CardSymbolType.FIVE));
        set.add(new Card(CardSuitType.SPADES, CardSymbolType.SIX));
        set.add(new Card(CardSuitType.SPADES, CardSymbolType.SEVEN));
        set.add(new Card(CardSuitType.SPADES, CardSymbolType.EIGHT));
        set.add(new Card(CardSuitType.SPADES, CardSymbolType.NINE));
        set.add(new Card(CardSuitType.SPADES, CardSymbolType.TEN));
        set.add(new Card(CardSuitType.SPADES, CardSymbolType.JACK));
        set.add(new Card(CardSuitType.SPADES, CardSymbolType.QUEEN));
        set.add(new Card(CardSuitType.SPADES, CardSymbolType.KING));

        set.add(new Card(CardSuitType.CLUBS, CardSymbolType.ACE));
        set.add(new Card(CardSuitType.CLUBS, CardSymbolType.TWO));
        set.add(new Card(CardSuitType.CLUBS, CardSymbolType.THREE));
        set.add(new Card(CardSuitType.CLUBS, CardSymbolType.FOUR));
        set.add(new Card(CardSuitType.CLUBS, CardSymbolType.FIVE));
        set.add(new Card(CardSuitType.CLUBS, CardSymbolType.SIX));
        set.add(new Card(CardSuitType.CLUBS, CardSymbolType.SEVEN));
        set.add(new Card(CardSuitType.CLUBS, CardSymbolType.EIGHT));
        set.add(new Card(CardSuitType.CLUBS, CardSymbolType.NINE));
        set.add(new Card(CardSuitType.CLUBS, CardSymbolType.TEN));
        set.add(new Card(CardSuitType.CLUBS, CardSymbolType.JACK));
        set.add(new Card(CardSuitType.CLUBS, CardSymbolType.QUEEN));
        set.add(new Card(CardSuitType.CLUBS, CardSymbolType.KING));

        set.add(new Card(CardSuitType.DIAMONDS, CardSymbolType.ACE));
        set.add(new Card(CardSuitType.DIAMONDS, CardSymbolType.TWO));
        set.add(new Card(CardSuitType.DIAMONDS, CardSymbolType.THREE));
        set.add(new Card(CardSuitType.DIAMONDS, CardSymbolType.FOUR));
        set.add(new Card(CardSuitType.DIAMONDS, CardSymbolType.FIVE));
        set.add(new Card(CardSuitType.DIAMONDS, CardSymbolType.SIX));
        set.add(new Card(CardSuitType.DIAMONDS, CardSymbolType.SEVEN));
        set.add(new Card(CardSuitType.DIAMONDS, CardSymbolType.EIGHT));
        set.add(new Card(CardSuitType.DIAMONDS, CardSymbolType.NINE));
        set.add(new Card(CardSuitType.DIAMONDS, CardSymbolType.TEN));
        set.add(new Card(CardSuitType.DIAMONDS, CardSymbolType.JACK));
        set.add(new Card(CardSuitType.DIAMONDS, CardSymbolType.QUEEN));
        set.add(new Card(CardSuitType.DIAMONDS, CardSymbolType.KING));

        set.add(new Card(CardSuitType.HEARTS, CardSymbolType.ACE));
        set.add(new Card(CardSuitType.HEARTS, CardSymbolType.TWO));
        set.add(new Card(CardSuitType.HEARTS, CardSymbolType.THREE));
        set.add(new Card(CardSuitType.HEARTS, CardSymbolType.FOUR));
        set.add(new Card(CardSuitType.HEARTS, CardSymbolType.FIVE));
        set.add(new Card(CardSuitType.HEARTS, CardSymbolType.SIX));
        set.add(new Card(CardSuitType.HEARTS, CardSymbolType.SEVEN));
        set.add(new Card(CardSuitType.HEARTS, CardSymbolType.EIGHT));
        set.add(new Card(CardSuitType.HEARTS, CardSymbolType.NINE));
        set.add(new Card(CardSuitType.HEARTS, CardSymbolType.TEN));
        set.add(new Card(CardSuitType.HEARTS, CardSymbolType.JACK));
        set.add(new Card(CardSuitType.HEARTS, CardSymbolType.QUEEN));
        set.add(new Card(CardSuitType.HEARTS, CardSymbolType.KING));

        cards.addAll(set);
        shuffle();
    }

    public Card getCard() {
        return cards.pop();
    }

    public void shuffle() {
        Random random = new Random();
        int numberOfShuffle = random.nextInt(100) + 30;
        int randStart = random.nextInt(CARD_NUMBER);
        Card removed = cards.remove(randStart);
        for (int i = 0; i < numberOfShuffle; i++) {
            removed = addRemove(random, removed);
        }
        cards.push(removed);
    }

    private Card addRemove(Random random, Card card) {
        int intRand = random.nextInt(CARD_NUMBER - 1);
        Card removed = cards.remove(intRand);
        cards.add(intRand, card);
        return removed;
    }
}
