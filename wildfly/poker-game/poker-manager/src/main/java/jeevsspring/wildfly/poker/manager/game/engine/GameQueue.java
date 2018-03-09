package jeevsspring.wildfly.poker.manager.game.engine;

import jeevsspring.wildfly.poker.manager.bo.BoClient;
import jeevsspring.wildfly.poker.manager.exception.PMException;
import jeevsspring.wildfly.poker.manager.game.Games;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

/**
 * @author Marco Romagnolo
 */
@Singleton
@LocalBean
public class GameQueue<E extends Game>  {

    @EJB
    private BoClient boClient;

    @EJB
    private Games<E> games;

    public E get(String tableId) throws PMException {
        if (!games.get(tableId).isRunning()) throw new PMException();
        else return games.get(tableId);
    }
}
