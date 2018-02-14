package jeevsspring.wildfly.poker.manager.engine.table;

import jeevsspring.wildfly.poker.manager.engine.game.GameType;

/**
 * @author Marco Romagnolo
 */
public class TableSettings {

    private int numberOfSeats;
    private long actionTimeOut;
    private GameType game;

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public long getActionTimeOut() {
        return actionTimeOut;
    }

    public void setActionTimeOut(long actionTimeOut) {
        this.actionTimeOut = actionTimeOut;
    }

    public GameType getGame() {
        return game;
    }

    public void setGame(GameType game) {
        this.game = game;
    }
}
