package jeevsspring.wildfly.games.poker.manager.message;

import jeevsspring.wildfly.games.poker.common.LobbyMessage;
import org.jboss.logging.Logger;

import javax.ejb.ActivationConfigProperty;
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
public class PokerLobbyMDBListener implements MessageListener {

    private Logger logger = Logger.getLogger(PokerLobbyMDBListener.class.getName());

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                String m = ((TextMessage) message).getText();
                logger.info("Message received : " + m);
            } else if (message instanceof LobbyMessage) {
                LobbyMessage m = message.getBody(LobbyMessage.class);
                logger.info("Message received : " + m);
            }
        } catch (JMSException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
