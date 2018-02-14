package jeevsspring.wildfly.poker.manager.engine.game;

import jeevsspring.wildfly.poker.manager.engine.hand.Hands;
import jeevsspring.wildfly.poker.manager.lobby.Lobby;

import javax.ejb.EJB;

/**
 * @author Marco Romagnolo
 */
public class TexasHoldem implements Runnable {

    @EJB
    private Lobby lobby;

    @EJB
    private Hands hands;

    @EJB
    private GameActions gameActions;

    @Override
    public void run() {

    }
}
