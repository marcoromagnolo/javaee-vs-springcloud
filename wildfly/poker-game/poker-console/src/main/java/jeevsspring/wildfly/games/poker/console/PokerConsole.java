package jeevsspring.wildfly.games.poker.console;

import jeevsspring.wildfly.games.poker.console.bean.Operator;
import jeevsspring.wildfly.games.poker.console.bean.Operators;

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
    private Operators operators;

    @Produces
    @Named
    public List<Operator> getOperators() {
        return operators.getOperators();
    }
}
