package jeevsspring.spring.poker.manager.lobby.api.json;

public class TableSettingsOut extends PlayerSessionOut {

    private String id;
    private String name;
    private int numberOfSeats;
    private long actionTimeout;
    private long startTimeout;
    private String gameType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
}
