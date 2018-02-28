package jeevsspring.wildfly.poker.manager.lobby;

import jeevsspring.wildfly.poker.common.TableSettings;
import jeevsspring.wildfly.poker.manager.bo.BoClient;
import jeevsspring.wildfly.poker.manager.engine.game.Game;
import jeevsspring.wildfly.poker.manager.engine.game.Games;
import jeevsspring.wildfly.poker.manager.engine.game.texasholdem.THGame;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class LobbyManager {

    private final Logger logger = Logger.getLogger(getClass());

    @EJB
    private BoClient boClient;

    @EJB
    private Games<Game> games;

    @EJB
    private LobbyTables lobbyTables;

    @PostConstruct
    public void init() {

        // Make this job forever
        while (true) {

            // Check and add game instances (async)
            if (!lobbyTables.getCreated().isEmpty()) {
                for (String tableId : lobbyTables.getCreated().keySet()) {
                    TableSettings settings = lobbyTables.getCreated().get(tableId);
                    THGame game = null;
                    switch (settings.getGameType()) {

                        case TEXAS_HOLDEM:
                            game = new THGame(tableId, settings, boClient);
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

            // Check and delete tables games (async)
            if (!lobbyTables.getDeleted().isEmpty()) {
                for (String tableId : lobbyTables.getUpdated().keySet()) {
                    games.get(tableId).delete();
                }
            }
        }
    }

}
