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
    private GameActions gameActions;

    @EJB
    private Games games;

    @PostConstruct
    private void init() {

        // Make this job forever
        while (true) {
            if (!handQueue.isEmpty()) {

                // Poll hand action made from players
                HandAction handAction = handQueue.poll();
                String tableId = handAction.getTableId();
                Game game = games.get(tableId);
                game.action(handAction);
                gameActions.addAll(tableId, game.getQueue()); // Action to return players

                // Wait Hand Finished to do buyin and buyout
                if (game.isHandFinished()) {
                    consumeTableQueue(tableId, game);
                }
            }
        }
    }

    private void consumeTableQueue(String tableId, Game game) {
        while (!tableQueue.isEmpty(tableId)) {
            TableAction tableAction = tableQueue.poll(tableId);
            game.action(tableAction);
            gameActions.addAll(tableId, game.getQueue()); // Action to return players
        }
    }
}
