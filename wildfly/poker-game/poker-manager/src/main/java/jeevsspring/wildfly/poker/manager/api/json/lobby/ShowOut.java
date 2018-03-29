package jeevsspring.wildfly.poker.manager.api.json.lobby;

import jeevsspring.wildfly.poker.manager.api.json.player.PlayerSessionOut;

import java.util.Collection;

public class ShowOut extends PlayerSessionOut {

    private Collection<LobbyTable> tables;

    public Collection<LobbyTable> getTables() {
        return tables;
    }

    public void setTables(Collection<LobbyTable> tables) {
        this.tables = tables;
    }

    @Override
    public String toString() {
        return "ShowOut{" +
                "tables=" + tables +
                "} " + super.toString();
    }
}
