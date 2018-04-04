package jeevsspring.spring.poker.common;

import java.io.Serializable;

/**
 * @author Marco Romagnolo
 */
public class TableSettings implements Serializable {

    private String name;
    private int numberOfSeats;
    private long actionTimeout;
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

    public long getActionTimeout() {
        return actionTimeout;
    }

    public void setActionTimeout(long actionTimeout) {
        this.actionTimeout = actionTimeout;
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

    @Override
    public String toString() {
        return "TableSettings{" +
                "name='" + name + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", actionTimeout=" + actionTimeout +
                ", startTimeout=" + startTimeout +
                ", gameType=" + gameType +
                '}';
    }
}
