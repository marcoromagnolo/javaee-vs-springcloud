package jeevsspring.wildfly.poker.manager.lobby;

import jeevsspring.wildfly.poker.common.TableSettings;
import jeevsspring.wildfly.poker.manager.util.IdGenerator;
import org.jboss.logging.Logger;

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

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    private Queue<TableSettings> created;

    private Queue<UpdatedTable> updated;

    private Queue<String> deleted;

    @PostConstruct
    public void init() {
        logger.trace("init()");
        this.created = new ArrayDeque<>();
        this.updated = new ArrayDeque<>();
        this.deleted = new ArrayDeque<>();
    }

    /**
     *
     * @return
     */
    public boolean createdExists() {
        return !created.isEmpty();
    }

    /**
     *
     * @return
     */
    public boolean updatedExists() {
        return !updated.isEmpty();
    }

    /**
     *
     * @return
     */
    public boolean deletedExists() {
        return !deleted.isEmpty();
    }

    /**
     * Poll created tables
     * @return
     */
    public TableSettings pollCreated() {
        logger.debug("pollCreated()");
        return created.poll();
    }

    /**
     * Poll updated tables
     * @return
     */
    public UpdatedTable pollUpdated() {
        logger.debug("pollUpdated()");
        return updated.poll();
    }

    /**
     * Poll deleted tables
     * @return
     */
    public String pollDeleted() {
        logger.debug("pollDeleted()");
        return deleted.poll();
    }

    /**
     * Create table
     * @param tableSettings
     * @return
     */
    public void create(TableSettings tableSettings) {
        logger.debug("create(" + tableSettings + ")");
        created.add(tableSettings);
    }

    /**
     * Update table
     * @param tableId
     * @param tableSettings
     */
    public void update(String tableId, TableSettings tableSettings) {
        logger.debug("create(" + tableId + ", " + tableSettings + ")");
        updated.add(new UpdatedTable(tableId, tableSettings));
    }

    /**
     * Drop table
     * @param tableId
     */
    public void delete(String tableId) {
        logger.debug("create(" + tableId + ")");
        deleted.add(tableId);
    }


}
