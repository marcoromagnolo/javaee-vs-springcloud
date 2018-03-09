package jeevsspring.wildfly.poker.manager.lobby;

import jeevsspring.wildfly.poker.common.TableSettings;

/**
 * @author Marco Romagnolo
 */
public class UpdatedTable {

    private String id;
    private TableSettings settings;

    public UpdatedTable(String id, TableSettings settings) {
        this.id = id;
        this.settings = settings;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TableSettings getSettings() {
        return settings;
    }

    public void setSettings(TableSettings settings) {
        this.settings = settings;
    }
}
