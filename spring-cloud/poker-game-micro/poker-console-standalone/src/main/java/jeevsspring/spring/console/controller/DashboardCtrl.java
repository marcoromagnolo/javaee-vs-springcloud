package jeevsspring.spring.console.controller;

import jeevsspring.spring.console.bean.ListOperatorBean;
import jeevsspring.spring.console.bean.ListTableGameBean;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.Serializable;

/**
 * @author Marco Romagnolo
 */
@Scope("request")
@Controller("dashboardCtrl")
public class DashboardCtrl implements Serializable {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private ListOperatorBean operators;

    @Autowired
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
