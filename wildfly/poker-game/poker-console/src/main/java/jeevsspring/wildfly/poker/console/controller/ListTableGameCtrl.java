package jeevsspring.wildfly.poker.console.controller;

import jeevsspring.wildfly.poker.common.LobbyMessage;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;
import java.io.Serializable;

public class ListTableGameCtrl implements Serializable {

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
