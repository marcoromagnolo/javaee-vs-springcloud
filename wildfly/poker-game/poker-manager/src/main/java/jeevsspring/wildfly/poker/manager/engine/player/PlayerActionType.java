package jeevsspring.wildfly.poker.manager.engine.player;

/**
 * @author Marco Romagnolo
 */
public enum PlayerActionType {

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
    FOLD,
    /**
     * Fold in a hand and show your cards.
     */
    FOLD_AND_SHOW,
    /**
     * Show the cards in showdown.
     */
    SHOW,
    /**
     * Muck the cards in showdown.
     */
    MUCK,
    SIT_IN,
    SIT_OUT
}
