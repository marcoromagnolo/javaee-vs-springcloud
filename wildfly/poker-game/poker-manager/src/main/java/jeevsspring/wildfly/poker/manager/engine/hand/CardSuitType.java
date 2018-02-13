package jeevsspring.wildfly.poker.manager.engine.hand;

/**
 * @author Marco Romagnolo
 */
public enum CardSuitType {
    HEARTS(4),
    DIAMONDS(3),
    CLUBS(2),
    SPADES(1);

    private final int value;

    CardSuitType(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
