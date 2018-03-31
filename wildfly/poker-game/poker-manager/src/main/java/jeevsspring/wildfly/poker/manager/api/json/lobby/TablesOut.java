package jeevsspring.wildfly.poker.manager.api.json.lobby;

import jeevsspring.wildfly.poker.manager.api.json.player.PlayerSessionOut;

import java.util.Collection;

public class TablesOut extends PlayerSessionOut {

    private Collection<Table> tables;

    public Collection<Table> getTables() {
        return tables;
    }

    public void setTables(Collection<Table> tables) {
        this.tables = tables;
    }

    @Override
    public String toString() {
        return "ShowOut{" +
                "tables=" + tables +
                "} " + super.toString();
    }
}
