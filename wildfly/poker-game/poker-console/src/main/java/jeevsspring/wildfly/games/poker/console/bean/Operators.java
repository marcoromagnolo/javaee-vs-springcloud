package jeevsspring.wildfly.games.poker.console.bean;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marco Romagnolo
 */
@Singleton
public class Operators {

    private List<Operator> operators;

    @PostConstruct
    public void init() {
        this.operators = new ArrayList<>();
    }

    public List<Operator> getOperators() {
        return operators;
    }

    public void setOperators(List<Operator> operators) {
        this.operators = operators;
    }
}
