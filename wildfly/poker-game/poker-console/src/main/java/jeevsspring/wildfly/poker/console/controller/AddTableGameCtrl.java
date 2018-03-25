package jeevsspring.wildfly.poker.console.controller;

import jeevsspring.wildfly.poker.common.LobbyActionType;
import jeevsspring.wildfly.poker.common.LobbyMessage;
import jeevsspring.wildfly.poker.common.TableSettings;
import jeevsspring.wildfly.poker.console.bean.TableGameBean;
import jeevsspring.wildfly.poker.console.message.LobbyMessageSender;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named("addTableGameCtrl")
@RequestScoped
public class AddTableGameCtrl implements Serializable {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @Inject
    private TableGameBean tableGame;

    @Inject
    private LobbyMessageSender sender;

    public void create() {
        logger.debug("create() tableGame: " + tableGame);

        TableSettings tableSettings = new TableSettings();
        tableSettings.setActionTimeout(tableGame.getActionTimeout());
        tableSettings.setGameType(tableGame.getGameType());
        tableSettings.setName(tableGame.getName());
        tableSettings.setNumberOfSeats(tableGame.getNumberOfSeats());
        tableSettings.setStartTimeout(tableGame.getStartTimeout());

        LobbyMessage message = new LobbyMessage();
        message.setActionType(LobbyActionType.CREATE);
        message.setTableSettings(tableSettings);
        sender.send(message);
    }
}
