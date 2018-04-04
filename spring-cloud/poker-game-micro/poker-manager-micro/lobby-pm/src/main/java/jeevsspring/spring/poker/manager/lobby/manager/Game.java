package jeevsspring.spring.poker.manager.lobby.manager;

import jeevsspring.spring.poker.common.TableSettings;

import java.util.UUID;
import java.util.logging.Logger;

public class Game {

    private final Logger logger = Logger.getLogger(getClass().toString());

    // Table Id
    private String tableId;

    // Table Settings
    private TableSettings settings;

    public Game(TableSettings settings) {
        this.tableId = UUID.randomUUID().toString();
        this.settings = settings;
    }

    public String getTableId() {
        return tableId;
    }

    public TableSettings getSettings() {
        return settings;
    }

    public void setSettings(TableSettings settings) {
        this.settings = settings;
    }
}
