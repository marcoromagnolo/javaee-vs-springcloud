package jeevsspring.wildfly.games.poker.manager;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class PokerManager extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(LobbyApi.class);
        return classes;
    }
}
