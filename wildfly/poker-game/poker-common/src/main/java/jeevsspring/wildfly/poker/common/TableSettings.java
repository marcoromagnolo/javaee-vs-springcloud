package jeevsspring.wildfly.poker.common;

/**
 * @author Marco Romagnolo
 */
public class TableSettings {

    private String name;
    private int numberOfSeats;
    private long actionTimeOut;
    private long startTimeout;
    private GameType gameType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public long getStartTimeout() {
        return startTimeout;
    }

    public void setStartTimeout(long startTimeout) {
        this.startTimeout = startTimeout;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }
}
