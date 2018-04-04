package jeevsspring.spring.poker.manager.lobby.api.json;

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
