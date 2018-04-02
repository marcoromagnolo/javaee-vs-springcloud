package jeevsspring.spring.console.controller;

import jeevsspring.spring.console.bean.TableGameBean;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.Serializable;

@Scope("request")
@Controller("addTableGameCtrl")
public class AddTableGameCtrl implements Serializable {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private TableGameBean tableGame;


    public void create() {
        logger.debug("create() tableGame: " + tableGame);

        TableGameBean tableSettings = new TableGameBean();
        tableSettings.setActionTimeout(tableGame.getActionTimeout());
        tableSettings.setGameType(tableGame.getGameType());
        tableSettings.setName(tableGame.getName());
        tableSettings.setNumberOfSeats(tableGame.getNumberOfSeats());
        tableSettings.setStartTimeout(tableGame.getStartTimeout());

        // TODO Send tableSettings by CREATE
    }
}
