package jeevsspring.wildfly.poker.console.controller;

import jeevsspring.wildfly.poker.console.bean.ListOperatorBean;
import jeevsspring.wildfly.poker.console.bean.ListTableGameBean;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

/**
 * @author Marco Romagnolo
 */
@Named
@RequestScoped
public class DashboardCtrl implements Serializable {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @Inject
    private ListOperatorBean operators;

    @Inject
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
