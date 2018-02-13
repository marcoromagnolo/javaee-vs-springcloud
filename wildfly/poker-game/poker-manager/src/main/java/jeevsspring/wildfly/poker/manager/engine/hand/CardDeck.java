package jeevsspring.wildfly.poker.manager.engine.hand;

import java.security.SecureRandom;
import java.util.Random;
import java.util.Stack;

/**
 * @author Marco Romagnolo
 */
public class CardDeck {

    public static final int CARD_NUMBER = 52;

    private Stack<Card> cards;

    public CardDeck() {
        cards.add(new Card(CardSuitType.SPADES, CardSymbolType.ACE));
        cards.add(new Card(CardSuitType.SPADES, CardSymbolType.TWO));
        cards.add(new Card(CardSuitType.SPADES, CardSymbolType.THREE));
        cards.add(new Card(CardSuitType.SPADES, CardSymbolType.FOUR));
        cards.add(new Card(CardSuitType.SPADES, CardSymbolType.FIVE));
        cards.add(new Card(CardSuitType.SPADES, CardSymbolType.SIX));
        cards.add(new Card(CardSuitType.SPADES, CardSymbolType.SEVEN));
        cards.add(new Card(CardSuitType.SPADES, CardSymbolType.EIGHT));
        cards.add(new Card(CardSuitType.SPADES, CardSymbolType.NINE));
        cards.add(new Card(CardSuitType.SPADES, CardSymbolType.TEN));
        cards.add(new Card(CardSuitType.SPADES, CardSymbolType.JACK));
        cards.add(new Card(CardSuitType.SPADES, CardSymbolType.QUEEN));
        cards.add(new Card(CardSuitType.SPADES, CardSymbolType.KING));

        cards.add(new Card(CardSuitType.CLUBS, CardSymbolType.ACE));
        cards.add(new Card(CardSuitType.CLUBS, CardSymbolType.TWO));
        cards.add(new Card(CardSuitType.CLUBS, CardSymbolType.THREE));
        cards.add(new Card(CardSuitType.CLUBS, CardSymbolType.FOUR));
        cards.add(new Card(CardSuitType.CLUBS, CardSymbolType.FIVE));
        cards.add(new Card(CardSuitType.CLUBS, CardSymbolType.SIX));
        cards.add(new Card(CardSuitType.CLUBS, CardSymbolType.SEVEN));
        cards.add(new Card(CardSuitType.CLUBS, CardSymbolType.EIGHT));
        cards.add(new Card(CardSuitType.CLUBS, CardSymbolType.NINE));
        cards.add(new Card(CardSuitType.CLUBS, CardSymbolType.TEN));
        cards.add(new Card(CardSuitType.CLUBS, CardSymbolType.JACK));
        cards.add(new Card(CardSuitType.CLUBS, CardSymbolType.QUEEN));
        cards.add(new Card(CardSuitType.CLUBS, CardSymbolType.KING));

        cards.add(new Card(CardSuitType.DIAMONDS, CardSymbolType.ACE));
        cards.add(new Card(CardSuitType.DIAMONDS, CardSymbolType.TWO));
        cards.add(new Card(CardSuitType.DIAMONDS, CardSymbolType.THREE));
        cards.add(new Card(CardSuitType.DIAMONDS, CardSymbolType.FOUR));
        cards.add(new Card(CardSuitType.DIAMONDS, CardSymbolType.FIVE));
        cards.add(new Card(CardSuitType.DIAMONDS, CardSymbolType.SIX));
        cards.add(new Card(CardSuitType.DIAMONDS, CardSymbolType.SEVEN));
        cards.add(new Card(CardSuitType.DIAMONDS, CardSymbolType.EIGHT));
        cards.add(new Card(CardSuitType.DIAMONDS, CardSymbolType.NINE));
        cards.add(new Card(CardSuitType.DIAMONDS, CardSymbolType.TEN));
        cards.add(new Card(CardSuitType.DIAMONDS, CardSymbolType.JACK));
        cards.add(new Card(CardSuitType.DIAMONDS, CardSymbolType.QUEEN));
        cards.add(new Card(CardSuitType.DIAMONDS, CardSymbolType.KING));

        cards.add(new Card(CardSuitType.HEARTS, CardSymbolType.ACE));
        cards.add(new Card(CardSuitType.HEARTS, CardSymbolType.TWO));
        cards.add(new Card(CardSuitType.HEARTS, CardSymbolType.THREE));
        cards.add(new Card(CardSuitType.HEARTS, CardSymbolType.FOUR));
        cards.add(new Card(CardSuitType.HEARTS, CardSymbolType.FIVE));
        cards.add(new Card(CardSuitType.HEARTS, CardSymbolType.SIX));
        cards.add(new Card(CardSuitType.HEARTS, CardSymbolType.SEVEN));
        cards.add(new Card(CardSuitType.HEARTS, CardSymbolType.EIGHT));
        cards.add(new Card(CardSuitType.HEARTS, CardSymbolType.NINE));
        cards.add(new Card(CardSuitType.HEARTS, CardSymbolType.TEN));
        cards.add(new Card(CardSuitType.HEARTS, CardSymbolType.JACK));
        cards.add(new Card(CardSuitType.HEARTS, CardSymbolType.QUEEN));
        cards.add(new Card(CardSuitType.HEARTS, CardSymbolType.KING));
    }

    public Stack<Card> getCards(int number) {
        Stack<Card> cards = new Stack<>();
        for (int i = 0; i < number; i++) {
            cards.push(cards.pop());
        }
        return cards;
    }

    public Card getCard() {
        return cards.pop();
    }

    public void shuffle() {
        Random rand = new SecureRandom();
        int number = rand.nextInt(52);
    }
}
