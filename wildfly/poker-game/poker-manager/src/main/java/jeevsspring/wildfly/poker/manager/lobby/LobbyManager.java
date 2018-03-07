package jeevsspring.wildfly.poker.manager.lobby;

import jeevsspring.wildfly.poker.common.TableSettings;
import jeevsspring.wildfly.poker.manager.bo.BoClient;
import jeevsspring.wildfly.poker.manager.engine.game.Game;
import jeevsspring.wildfly.poker.manager.engine.game.Games;
import jeevsspring.wildfly.poker.manager.engine.game.texasholdem.THGame;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import java.util.Calendar;

@Singleton
@Startup
public class LobbyManager {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @Resource
    TimerService timerService;

    @EJB
    private BoClient boClient;

    @EJB
    private Games<Game> games;

    @EJB
    private LobbyTables lobbyTables;

    @PostConstruct
    public void init() {
        timerService.createTimer(0, 5000, "Every seconds");
    }

    @Timeout
    public void doWork(Timer timer) {
        logger.trace("LobbyManager :: doWork() timer info: " + timer.getInfo().toString());
        logger.trace("LobbyManager :: doWork() started at: " + Calendar.getInstance().getTime());

        // Check and add game instances (async)
        if (!lobbyTables.getCreated().isEmpty()) {
            for (String tableId : lobbyTables.getCreated().keySet()) {
                TableSettings settings = lobbyTables.getCreated().get(tableId);
                THGame game = null;
                switch (settings.getGameType()) {

                    case TEXAS_HOLDEM:
                        logger.debug("LobbyManager :: doWork() create texas Holdem Game at: " + Calendar.getInstance().getTime());
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
                logger.debug("LobbyManager :: doWork() update game at: " + Calendar.getInstance().getTime());
                games.get(tableId).update(settings);
            }
        }

        // Check and delete tables games (async)
        if (!lobbyTables.getDeleted().isEmpty()) {
            for (String tableId : lobbyTables.getUpdated().keySet()) {
                logger.debug("LobbyManager :: doWork() delete game at: " + Calendar.getInstance().getTime());
                games.get(tableId).delete();
            }
        }

        logger.trace("LobbyManager :: doWork() finished at: " + Calendar.getInstance().getTime());
    }

}
