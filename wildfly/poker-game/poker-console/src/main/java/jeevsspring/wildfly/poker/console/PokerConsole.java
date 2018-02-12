package jeevsspring.wildfly.poker.console;

import jeevsspring.wildfly.poker.console.bean.ConnectedOperators;
import jeevsspring.wildfly.poker.console.bean.Operator;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * @author Marco Romagnolo
 */
@Model
public class PokerConsole {

    @Inject
    private ConnectedOperators connectedOperators;

    @Produces
    @Named
    public List<Operator> getConnectedOperators() {
        return connectedOperators.getOperators();
    }
}
