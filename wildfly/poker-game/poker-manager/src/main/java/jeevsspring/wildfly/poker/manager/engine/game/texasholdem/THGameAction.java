package jeevsspring.wildfly.poker.manager.engine.game.texasholdem;

import jeevsspring.wildfly.poker.manager.engine.game.GameAction;

public class THGameAction extends GameAction {

    private int smallBlind;
    private int bigBlind;
    private THGame.RoundPhase roundPhase;

    public THGameAction(String handId) {
        super(handId);
    }

    public int getSmallBlind() {
        return smallBlind;
    }

    public void setSmallBlind(int smallBlind) {
        this.smallBlind = smallBlind;
    }

    public int getBigBlind() {
        return bigBlind;
    }

    public void setBigBlind(int bigBlind) {
        this.bigBlind = bigBlind;
    }

    public THGame.RoundPhase getRoundPhase() {
        return roundPhase;
    }

    public void setRoundPhase(THGame.RoundPhase roundPhase) {
        this.roundPhase = roundPhase;
    }
}
