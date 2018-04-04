package jeevsspring.spring.console.controller;

import jeevsspring.spring.console.bean.TableGameBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Scope("request")
@Controller("addTableGameCtrl")
public class AddTableGameCtrl implements Serializable {

    private final Logger logger = Logger.getLogger(getClass().toString());

    @Autowired
    private TableGameBean tableGame;


    public void create() {
        logger.log(Level.FINE, "create() tableGame: " + tableGame);

        TableGameBean tableSettings = new TableGameBean();
        tableSettings.setActionTimeout(tableGame.getActionTimeout());
        tableSettings.setGameType(tableGame.getGameType());
        tableSettings.setName(tableGame.getName());
        tableSettings.setNumberOfSeats(tableGame.getNumberOfSeats());
        tableSettings.setStartTimeout(tableGame.getStartTimeout());

        // TODO Send tableSettings by CREATE
    }
}
