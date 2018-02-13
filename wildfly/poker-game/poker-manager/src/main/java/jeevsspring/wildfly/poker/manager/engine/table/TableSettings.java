package jeevsspring.wildfly.poker.manager.engine.table;

/**
 * @author Marco Romagnolo
 */
public class TableSettings {

    private int numberOfSeats;
    private long actionTimeOut;

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
}
