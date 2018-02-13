package jeevsspring.wildfly.poker.manager.lobby;

import jeevsspring.wildfly.poker.manager.engine.table.Table;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marco Romagnolo
 */
@Stateful
@LocalBean
public class Lobby {

    private Map<String, Table> tables;

    @PostConstruct
    public void init() {
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
