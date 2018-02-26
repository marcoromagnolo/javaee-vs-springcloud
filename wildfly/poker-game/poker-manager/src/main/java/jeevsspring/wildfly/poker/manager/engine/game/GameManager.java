package jeevsspring.wildfly.poker.manager.engine.game;

import jeevsspring.wildfly.poker.manager.engine.hand.HandAction;
import jeevsspring.wildfly.poker.manager.engine.hand.HandActionQueue;
import jeevsspring.wildfly.poker.manager.engine.table.TableAction;
import jeevsspring.wildfly.poker.manager.engine.table.TableActionQueue;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
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

            // Start all startable games
            for (Game game : games.getAll()) {
                if (game.isStartable()) {
                    game.start();
                }
            }

            // Check Hand Queue
            if (!handQueue.isEmpty()) {

                // Poll hand action made from players
                HandAction handAction = handQueue.poll();
                String tableId = handAction.getTableId();
                Game game = games.get(tableId);
                game.action(handAction);

                // Wait Hand Finished to do buyin, buyout, sitin and sitout
                if (!game.isRoundRunning()) {
                    consumeTableQueue(tableId, game);
                }
            }
        }
    }

    private void consumeTableQueue(String tableId, Game game) {
        while (!tableQueue.isEmpty(tableId)) {
            TableAction tableAction = tableQueue.poll(tableId);
            game.action(tableAction);
        }
    }
}
