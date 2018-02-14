package jeevsspring.wildfly.poker.manager.lobby;

import jeevsspring.wildfly.poker.manager.engine.game.GameExecutor;
import jeevsspring.wildfly.poker.manager.engine.game.TexasHoldem;
import jeevsspring.wildfly.poker.manager.engine.table.Table;
import jeevsspring.wildfly.poker.manager.engine.table.TableSettings;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Marco Romagnolo
 */
@Stateful
@LocalBean
public class Lobby {

    private Map<String, Table> tables;
    private Map<String, String> playerSessions;

    @PostConstruct
    public void init() {
        this.tables = new HashMap<>();
        this.playerSessions = new HashMap<>();
    }

    public String getPlayerId(String sessionId) {
        return playerSessions.get(sessionId);
    }

    public String createPlayerSession(String playerId, String sessionId) {
        return playerSessions.put(playerId, sessionId);
    }

    public String dropPlayerSession(String sessionId) {
        return playerSessions.remove(sessionId);
    }

    public String login(String sessionId, String playerId) {
        return playerSessions.put(sessionId, playerId);
    }

    public String logout(String sessionId) {
        return playerSessions.remove(sessionId);
    }

    public Collection<Table> getTables() {
        return tables.values();
    }

    public Table getTable(String tableId) {
        return tables.get(tableId);
    }

    public void createTable(TableSettings tableSettings) {
        Table table = new Table(tableSettings);
        tables.put(table.getId(), table);
    }

    public void cancel(String tableId) {
        Table table =  tables.get(tableId);
        table.setPublished(false);
    }

    public boolean remove(Table table) {
        return tables.put(table.getId(), table) != null;
    }

}
