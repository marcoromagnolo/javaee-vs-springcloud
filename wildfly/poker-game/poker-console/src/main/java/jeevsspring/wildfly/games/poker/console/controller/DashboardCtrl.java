package jeevsspring.wildfly.games.poker.console.controller;

import jeevsspring.wildfly.games.poker.console.bean.ConnectedOperators;

/**
 * @author Marco Romagnolo
 */
public class DashboardCtrl {
    private ConnectedOperators operators;

    public void setOperators(ConnectedOperators operators) {
        this.operators = operators;
    }

    public ConnectedOperators getOperators() {
        return operators;
    }
}
