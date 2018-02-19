package jeevsspring.wildfly.poker.manager;

import jeevsspring.wildfly.poker.manager.api.HandApi;
import jeevsspring.wildfly.poker.manager.api.LobbyApi;
import jeevsspring.wildfly.poker.manager.api.TableApi;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class PokerManager extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(LobbyApi.class);
        classes.add(TableApi.class);
        classes.add(HandApi.class);
        return classes;
    }
}
