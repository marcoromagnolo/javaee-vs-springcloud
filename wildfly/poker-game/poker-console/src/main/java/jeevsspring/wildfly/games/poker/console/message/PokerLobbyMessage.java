package jeevsspring.wildfly.games.poker.console.message;

import jeevsspring.wildfly.games.poker.common.LobbyMessage;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

/**
 * @author Marco Romagnolo
 */
public class PokerLobbyMessage {

    @Inject
    JMSContext context;

    @Resource(mappedName = "java:/jms/queue/pokerLobbyQueue")
    private Queue queue;

    public void send(String message) {
        context.createProducer()
                .send(queue, message);
    }

    public void send(LobbyMessage message) {
        context.createProducer()
                .send(queue, message);
    }

}
