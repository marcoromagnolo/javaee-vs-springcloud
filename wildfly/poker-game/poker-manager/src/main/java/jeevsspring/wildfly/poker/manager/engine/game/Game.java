package jeevsspring.wildfly.poker.manager.engine.game;

import jeevsspring.wildfly.poker.common.TableSettings;
import jeevsspring.wildfly.poker.manager.engine.hand.HandAction;
import jeevsspring.wildfly.poker.manager.engine.table.TableAction;

import java.util.Queue;

public abstract class Game {

    private final TableSettings settings;

    private final String tableId;

    private Queue<GameAction> queue;

    private boolean handFinished;

    private TableSettings updateSettings;

    private boolean stop;

    public Game(String tableId, TableSettings settings) {
        this.tableId = tableId;
        this.settings = settings;
    }

    public String getTableId() {
        return tableId;
    }

    public Queue<GameAction> getQueue() {
        return queue;
    }

    public TableSettings getSettings() {
        return settings;
    }

    public abstract void action(HandAction action);

    public abstract void action(TableAction action);

    public boolean isHandFinished() {
        return handFinished;
    }

    public void setHandFinished(boolean handFinished) {
        this.handFinished = handFinished;
    }

    public void update(TableSettings settings) {
        updateSettings = settings;
    }

    public void stop() {
        stop = true;
    }

}
