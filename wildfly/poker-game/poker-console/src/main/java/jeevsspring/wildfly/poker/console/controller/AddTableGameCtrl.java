package jeevsspring.wildfly.poker.console.controller;

import jeevsspring.wildfly.poker.common.LobbyActionType;
import jeevsspring.wildfly.poker.common.LobbyMessage;
import jeevsspring.wildfly.poker.console.bean.TableGameBean;
import jeevsspring.wildfly.poker.console.message.LobbyMessageSender;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@RequestScoped
public class AddTableGameCtrl implements Serializable {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @Inject
    private TableGameBean tableGame;

    @Inject
    private LobbyMessageSender sender;

    public void create() {
        logger.debug("AddTableGameCtrl :: create() tableGame: " + tableGame);
        LobbyMessage message = new LobbyMessage();
        message.setActionType(LobbyActionType.CREATE);
        message.setTableSettings(tableGame);
        sender.send(message);
    }
}
