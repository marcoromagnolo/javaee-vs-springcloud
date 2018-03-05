package jeevsspring.wildfly.poker.console.controller;

import jeevsspring.wildfly.poker.console.bean.TableGameBean;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Queue;

public class AddTableGameCtrl {

    private TableGameBean tableGame;

    @Inject
    JMSContext context;

    @Resource(mappedName = "java:/jms/queue/pokerLobbyQueue")
    private Queue queue;

    public void send(String message) {
        context.createProducer()
                .send(queue, message);
    }

    public void send(jeevsspring.wildfly.poker.common.LobbyMessage message) {
        context.createProducer()
                .send(queue, message);
    }

    public void setTableGame(TableGameBean tableGame) {
        this.tableGame = tableGame;
    }

    public TableGameBean getTableGame() {
        return tableGame;
    }
}
