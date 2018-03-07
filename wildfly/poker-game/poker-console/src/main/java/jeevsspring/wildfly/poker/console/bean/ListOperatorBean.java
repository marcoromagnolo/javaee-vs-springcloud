package jeevsspring.wildfly.poker.console.bean;

import jeevsspring.wildfly.poker.console.util.OperatorSession;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Marco Romagnolo
 */
@Named
@RequestScoped
public class ListOperatorBean implements Serializable {

    private List<OperatorSession> operators;

    @PostConstruct
    public void init() {
        this.operators = new ArrayList<>();
    }

    public List<OperatorSession> getOperators() {
        return operators;
    }

    public void setOperators(List<OperatorSession> operators) {
        this.operators = operators;
    }
}
