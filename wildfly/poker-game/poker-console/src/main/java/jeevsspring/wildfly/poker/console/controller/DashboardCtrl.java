package jeevsspring.wildfly.poker.console.controller;

import jeevsspring.wildfly.poker.console.bean.ConnectedOperators;

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
