package jeevsspring.wildfly.poker.manager.engine.game;

import jeevsspring.wildfly.poker.manager.engine.hand.HandActions;
import jeevsspring.wildfly.poker.manager.engine.table.TableActionQueue;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class GameManager {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @EJB
    private HandActions handActions;

    @EJB
    private TableActionQueue tableQueue;

    @EJB
    private Games<Game> games;

    @PostConstruct
    private void init() {

        // Make this job forever
        while (true) {

            // Poll Hand and Table actions made from players
            games.getAll().parallelStream()
                    .forEach(game -> game.doActions(
                            handActions.get(game.getTableId()),
                            tableQueue.poll(game.getTableId())
                    ));
        }
    }

}
