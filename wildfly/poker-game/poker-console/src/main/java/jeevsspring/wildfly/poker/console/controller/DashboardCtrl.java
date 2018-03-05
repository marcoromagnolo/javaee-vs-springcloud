package jeevsspring.wildfly.poker.console.controller;

import jeevsspring.wildfly.poker.console.bean.ListOperatorBean;
import jeevsspring.wildfly.poker.console.bean.ListTableGameBean;

/**
 * @author Marco Romagnolo
 */
public class DashboardCtrl {
    private ListOperatorBean operators;
    private ListTableGameBean tableGames;

    public void setOperators(ListOperatorBean operators) {
        this.operators = operators;
    }

    public ListOperatorBean getOperators() {
        return operators;
    }

    public void setTableGames(ListTableGameBean tableGames) {
        this.tableGames = tableGames;
    }

    public ListTableGameBean getTableGames() {
        return tableGames;
    }
}
