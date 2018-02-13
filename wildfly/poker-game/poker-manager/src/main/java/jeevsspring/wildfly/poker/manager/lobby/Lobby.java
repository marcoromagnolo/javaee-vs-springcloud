package jeevsspring.wildfly.poker.manager.lobby;

import jeevsspring.wildfly.poker.manager.engine.table.Table;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marco Romagnolo
 */
public class Lobby {

    private final Map<String, Table> tables;

    public Lobby() {
        this.tables = new HashMap<>();
    }

    public boolean publish(Table table) {
        table.setPublished(true);
        return tables.put(table.getId(), table) != null;
    }

    public boolean cancel(Table table) {
        Table current =  tables.get(table.getId());
        current.setPublished(false);
        return true;
    }

    public boolean remove(Table table) {
        return tables.put(table.getId(), table) != null;
    }
}
