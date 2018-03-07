package jeevsspring.wildfly.poker.console.message;

import jeevsspring.wildfly.poker.common.LobbyMessage;
import org.jboss.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

/**
 * @author Marco Romagnolo
 */
@RequestScoped
public class LobbyMessageSender {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @Inject
    JMSContext context;

    @Resource(mappedName = "java:/jms/queue/pokerLobbyQueue")
    private Queue queue;

    public void send(LobbyMessage message) {
        logger.debug("AddTableGameCtrl :: create(" + message + ")");
        context.createProducer()
                .send(queue, message);
    }
}
