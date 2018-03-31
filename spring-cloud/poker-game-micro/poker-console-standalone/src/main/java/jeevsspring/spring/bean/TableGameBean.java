package jeevsspring.spring.bean;

import org.springframework.web.context.annotation.RequestScope;

import javax.inject.Named;
import java.io.Serializable;

@Named("tableGame")
@RequestScope
public class TableGameBean implements Serializable {

    private String name;
    private int numberOfSeats;
    private long actionTimeout;
    private long startTimeout;
    private String gameType;

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

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    @Override
    public String toString() {
        return "TableGameBean{" +
                "name='" + name + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", actionTimeout=" + actionTimeout +
                ", startTimeout=" + startTimeout +
                ", gameType=" + gameType +
                '}';
    }
}
