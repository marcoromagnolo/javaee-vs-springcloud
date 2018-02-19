package jeevsspring.wildfly.poker.manager.engine.game;

import jeevsspring.wildfly.poker.common.TableSettings;
import jeevsspring.wildfly.poker.manager.engine.hand.HandAction;
import jeevsspring.wildfly.poker.manager.engine.table.TableAction;

public abstract class Game {

    private final TableSettings settings;

    private final String tableId;

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

    public TableSettings getSettings() {
        return settings;
    }

    public abstract GameAction action(HandAction action);

    public abstract GameAction action(TableAction action);

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

    public abstract GameAction sync();
}
