package jeevsspring.wildfly.poker.manager.lobby;

import jeevsspring.wildfly.poker.common.TableSettings;
import jeevsspring.wildfly.poker.manager.util.IdGenerator;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marco Romagnolo
 */
@Singleton
@LocalBean
public class LobbyTables {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    private Map<String, TableSettings> created;

    private Map<String, TableSettings> updated;

    private List<String> deleted;

    @PostConstruct
    public void init() {
        logger.trace("LobbyTables :: init()");
        this.created = new HashMap<>();
        this.updated = new HashMap<>();
        this.deleted = new ArrayList<>();
    }

    /**
     * Get create tables
     * @return
     */
    public Map<String, TableSettings> getCreated() {
        logger.trace("LobbyTables :: getCreated()");
        return created;
    }

    /**
     * Get update tables
     * @return
     */
    public Map<String, TableSettings> getUpdated() {
        logger.trace("LobbyTables :: getUpdated()");
        return updated;
    }

    /**
     * Get drop tables
     * @return
     */
    public List<String> getDeleted() {
        logger.trace("LobbyTables :: getDeleted()");
        return deleted;
    }

    /**
     * Create table
     * @param tableSettings
     * @return
     */
    public String create(TableSettings tableSettings) {
        logger.trace("LobbyTables :: create(" + tableSettings + ")");
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
        logger.trace("LobbyTables :: create(" + tableId + ", " + tableSettings + ")");
        updated.put(tableId, tableSettings);
    }

    /**
     * Drop table
     * @param tableId
     */
    public void delete(String tableId) {
        logger.trace("LobbyTables :: create(" + tableId + ")");
        deleted.add(tableId);
    }

}
