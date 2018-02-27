package jeevsspring.wildfly.poker.manager.engine.game;

import jeevsspring.wildfly.poker.manager.engine.hand.HandActionQueue;
import jeevsspring.wildfly.poker.manager.engine.table.TableActionQueue;

import javax.annotation.PostConstruct;
import javax.ejb.*;

@Singleton
@Startup
public class GameManager {

    @EJB
    private HandActionQueue handQueue;

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
                            handQueue.poll(game.getTableId()),
                            tableQueue.poll(game.getTableId())
                    ));
        }
    }

}
