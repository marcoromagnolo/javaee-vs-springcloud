package jeevsspring.wildfly.poker.manager.lobby;

import jeevsspring.wildfly.poker.common.TableSettings;
import jeevsspring.wildfly.poker.manager.bo.BoClient;
import jeevsspring.wildfly.poker.manager.engine.game.Game;
import jeevsspring.wildfly.poker.manager.engine.game.GameActions;
import jeevsspring.wildfly.poker.manager.engine.game.Games;
import jeevsspring.wildfly.poker.manager.engine.game.TexasHoldem;
import jeevsspring.wildfly.poker.manager.engine.table.TableAction;
import jeevsspring.wildfly.poker.manager.engine.table.TableActionQueue;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class LobbyManager {

    private final Logger logger = Logger.getLogger(getClass());

    @EJB
    private BoClient boClient;

    @EJB
    private Games games;

    @EJB
    private LobbyTables lobbyTables;

    @EJB
    private TableActionQueue tableQueue;

    @EJB
    private GameActions gameActions;


    @PostConstruct
    public void init() {

        // Make this job forever
        while (true) {

            // Check and add game instances (async)
            if (!lobbyTables.getCreated().isEmpty()) {
                for (String tableId : lobbyTables.getCreated().keySet()) {
                    TableSettings settings = lobbyTables.getCreated().get(tableId);
                    Game game = null;
                    switch (settings.getGameType()) {

                        case TEXAS_HOLDEM:
                            game = new TexasHoldem(tableId, settings, boClient);
                            break;

                        default:
                            logger.error("Game type unknown:  " + settings.getGameType());

                    }
                    games.add(game);
                }
            }

            // Check and update tables games (async)
            if (!lobbyTables.getUpdated().isEmpty()) {
                for (String tableId : lobbyTables.getUpdated().keySet()) {
                    TableSettings settings = lobbyTables.getCreated().get(tableId);
                    games.get(tableId).update(settings);
                }
            }

            // Check and drop tables games (async)
            if (!lobbyTables.getDropped().isEmpty()) {
                for (String tableId : lobbyTables.getUpdated().keySet()) {
                    games.get(tableId).stop();
                }
            }

            // Consume all games not playing with table queue
            for (Game game : games.getAll()) {
                if (!game.isPlaying() && !tableQueue.isEmpty(game.getTableId())) {
                    consumeTableQueue(game.getTableId(), game);
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
