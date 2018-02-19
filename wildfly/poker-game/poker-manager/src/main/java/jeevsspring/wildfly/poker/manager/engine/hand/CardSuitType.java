package jeevsspring.wildfly.poker.manager.engine.hand;

/**
 * @author Marco Romagnolo
 */
public enum CardSuitType {
    HEARTS("H"),
    DIAMONDS("D"),
    CLUBS("C"),
    SPADES("S");

    private final String value;

    CardSuitType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
