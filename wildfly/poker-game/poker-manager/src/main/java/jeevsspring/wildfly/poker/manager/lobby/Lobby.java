package jeevsspring.wildfly.poker.manager.lobby;

import jeevsspring.wildfly.poker.manager.engine.player.Player;
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
    private Map<String, String> loggedPlayers;

    @PostConstruct
    public void init() {
        this.tables = new HashMap<>();
        this.loggedPlayers = new HashMap<>();
    }

    public String login(String sessionId, String playerId) {
        return loggedPlayers.put(sessionId, playerId);
    }

    public String getPlayer(String sessionId) {
        return loggedPlayers.get(sessionId);
    }

    public String logout(String sessionId) {
        return loggedPlayers.remove(sessionId);
    }

    public Map<String, Table> getTables() {
        return tables;
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
