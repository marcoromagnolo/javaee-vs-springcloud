package jeevsspring.wildfly.poker.manager.lobby;

import jeevsspring.wildfly.poker.common.TableSettings;
import jeevsspring.wildfly.poker.manager.util.IdGenerator;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import java.util.*;

/**
 * @author Marco Romagnolo
 */
@Singleton
@LocalBean
public class LobbyTables {

    private Map<String, TableSettings> created;

    private Map<String, TableSettings> updated;

    private List<String> dropped;

    @PostConstruct
    public void init() {
        this.created = new HashMap<>();
        this.updated = new HashMap<>();
        this.dropped = new ArrayList<>();
    }

    /**
     * Get create tables
     * @return
     */
    public Map<String, TableSettings> getCreated() {
        return created;
    }

    /**
     * Get update tables
     * @return
     */
    public Map<String, TableSettings> getUpdated() {
        return updated;
    }

    /**
     * Get drop tables
     * @return
     */
    public List<String> getDropped() {
        return dropped;
    }

    /**
     * Create table
     * @param tableSettings
     * @return
     */
    public String create(TableSettings tableSettings) {
        String tableId = IdGenerator.newTableId();
        created.put(tableId, tableSettings);
        return tableId;
    }

    /**
     * Update table
     * @param tableId
     * @param tableSettings
     */
    public void update(String tableId, TableSettings tableSettings) {
        updated.put(tableId, tableSettings);
    }

    /**
     * Drop table
     * @param tableId
     */
    public void drop(String tableId) {
        dropped.add(tableId);
    }

}
