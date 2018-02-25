package jeevsspring.wildfly.poker.manager.engine.hand;

public enum HandActionType {
    /**
     * Raise in a betting round.
     */
    RAISE,
    /**
     * Bet in a betting round.
     */
    BET,
    /**
     * Call a bet in a betting round.
     */
    CALL,
    /**
     * Check in a betting round.
     */
    CHECK,
    /**
     * Fold in a hand.
     */
    FOLD
}
