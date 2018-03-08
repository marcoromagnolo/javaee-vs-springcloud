package jeevsspring.wildfly.poker.manager.game.engine.texasholdem;

import jeevsspring.wildfly.poker.manager.game.engine.GameAction;
import org.jboss.logging.Logger;

public class THGameAction extends GameAction {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    // Small Blind
    private int smallBlind;

    // Big Blind
    private int bigBlind;

    // Round Phase
    private THGame.RoundPhase roundPhase;

    public THGameAction(String handId) {
        super(handId);
        logger.trace("THGameAction(" + handId + ")");
    }

    public int getSmallBlind() {
        logger.trace("getSmallBlind()");
        return smallBlind;
    }

    public void setSmallBlind(int smallBlind) {
        logger.trace("setSmallBlind(" + smallBlind + ")");
        this.smallBlind = smallBlind;
    }

    public int getBigBlind() {
        logger.trace("getBigBlind()");
        return bigBlind;
    }

    public void setBigBlind(int bigBlind) {
        logger.trace("setBigBlind(" + bigBlind + ")");
        this.bigBlind = bigBlind;
    }

    public THGame.RoundPhase getRoundPhase() {
        logger.trace("getRoundPhase()");
        return roundPhase;
    }

    public void setRoundPhase(THGame.RoundPhase roundPhase) {
        logger.trace("setRoundPhase(" + roundPhase + ")");
        this.roundPhase = roundPhase;
    }
}
