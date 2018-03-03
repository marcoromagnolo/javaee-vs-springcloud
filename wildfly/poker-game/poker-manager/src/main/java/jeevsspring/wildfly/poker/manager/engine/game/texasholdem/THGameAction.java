package jeevsspring.wildfly.poker.manager.engine.game.texasholdem;

import jeevsspring.wildfly.poker.manager.engine.game.GameAction;
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
        logger.trace("THGameAction :: Constructor(" + handId + ")");
    }

    public int getSmallBlind() {
        logger.trace("THGameAction :: getSmallBlind()");
        return smallBlind;
    }

    public void setSmallBlind(int smallBlind) {
        logger.trace("THGameAction :: setSmallBlind(" + smallBlind + ")");
        this.smallBlind = smallBlind;
    }

    public int getBigBlind() {
        logger.trace("THGameAction :: getBigBlind()");
        return bigBlind;
    }

    public void setBigBlind(int bigBlind) {
        logger.trace("THGameAction :: setBigBlind(" + bigBlind + ")");
        this.bigBlind = bigBlind;
    }

    public THGame.RoundPhase getRoundPhase() {
        logger.trace("THGameAction :: getRoundPhase()");
        return roundPhase;
    }

    public void setRoundPhase(THGame.RoundPhase roundPhase) {
        logger.trace("THGameAction :: setRoundPhase(" + roundPhase + ")");
        this.roundPhase = roundPhase;
    }
}
