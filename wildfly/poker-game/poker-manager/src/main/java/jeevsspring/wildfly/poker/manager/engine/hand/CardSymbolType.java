package jeevsspring.wildfly.poker.manager.engine.hand;

/**
 * @author Marco Romagnolo
 */
public enum CardSymbolType {
    ACE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(11),
    QUEEN(12),
    KING(13);

    private final int value;

    CardSymbolType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
