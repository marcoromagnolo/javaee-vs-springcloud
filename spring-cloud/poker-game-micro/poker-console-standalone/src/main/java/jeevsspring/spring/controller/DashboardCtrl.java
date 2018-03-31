package jeevsspring.spring.controller;

import jeevsspring.spring.bean.ListOperatorBean;
import jeevsspring.spring.bean.ListTableGameBean;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

import javax.inject.Named;
import java.io.Serializable;

/**
 * @author Marco Romagnolo
 */
@Named("dashboardCtrl")
@RequestScope
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
