package jeevsspring.wildfly.poker.manager.message;

import jeevsspring.wildfly.poker.common.LobbyMessage;
import jeevsspring.wildfly.poker.manager.lobby.LobbyTables;
import org.jboss.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @author Marco Romagnolo
 */
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/queue/pokerLobbyQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")})
public class LobbyMessageListener implements MessageListener {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @EJB
    private LobbyTables lobbyTables;

    @Override
    public void onMessage(Message message) {
        logger.debug("PokerLobbyMDBListener :: onMessage(" + message + ")");
        try {
            LobbyMessage lobbyMessage = message.getBody(LobbyMessage.class);
            logger.info("Message received : " + lobbyMessage);
            switch (lobbyMessage.getActionType()) {

                case CREATE:
                    lobbyTables.create(lobbyMessage.getTableSettings());
                    break;
                case DROP:
                    lobbyTables.delete(lobbyMessage.getTableId());
                    break;
                case UPDATE:
                    lobbyTables.update(lobbyMessage.getTableId(), lobbyMessage.getTableSettings());
                    break;
            }
            logger.info("Message received : " + lobbyMessage);
        } catch (JMSException e) {
            logger.error(e);
        }
    }
}
