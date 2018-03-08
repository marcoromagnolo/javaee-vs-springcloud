package jeevsspring.wildfly.poker.manager.game.hand;

/**
 * @author Marco Romagnolo
 */
public enum CardSymbolType {
    ACE("1"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    JACK("J"),
    QUEEN("Q"),
    KING("K");

    private final String value;

    CardSymbolType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
