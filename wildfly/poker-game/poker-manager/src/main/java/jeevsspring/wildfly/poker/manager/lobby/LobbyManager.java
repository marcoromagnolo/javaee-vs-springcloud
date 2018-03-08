package jeevsspring.wildfly.poker.manager.lobby;

import jeevsspring.wildfly.poker.common.TableSettings;
import jeevsspring.wildfly.poker.manager.bo.BoClient;
import jeevsspring.wildfly.poker.manager.game.engine.Game;
import jeevsspring.wildfly.poker.manager.game.Games;
import jeevsspring.wildfly.poker.manager.game.engine.texasholdem.THGame;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import java.util.Calendar;
import java.util.Map;

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
    public void process(Timer timer) {
        logger.trace("process() timer info: " + timer.getInfo().toString());
        logger.trace("process() started at: " + Calendar.getInstance().getTime());

        // Check Queue and add game instances
        if (!lobbyTables.getCreated().isEmpty()) {
            for (Map.Entry<String, TableSettings> entry : lobbyTables.getCreated().entrySet()) {
                String tableId = entry.getKey();
                TableSettings settings = entry.getValue();
                THGame game = null;
                try {
                    switch (settings.getGameType()) {

                        case TEXAS_HOLDEM:
                            logger.debug("process() create Texas Holdem Game at: " + Calendar.getInstance().getTime());
                            game = new THGame(tableId, settings, boClient);
                            break;

                        default:
                            logger.error("Game type unknown:  " + settings.getGameType());

                    }
                    games.add(game);
                    logger.debug("process() New Game instance: " + entry);
                } catch (Exception e) {
                    logger.error(e);
                }
            }
        }

        // Check and update tables games (async)
        if (!lobbyTables.getUpdated().isEmpty()) {
            for (String tableId : lobbyTables.getUpdated().keySet()) {
                TableSettings settings = lobbyTables.getCreated().get(tableId);
                logger.debug("process() update game at: " + Calendar.getInstance().getTime());
                games.get(tableId).update(settings);
            }
        }

        // Check and delete tables games (async)
        if (!lobbyTables.getDeleted().isEmpty()) {
            for (String tableId : lobbyTables.getUpdated().keySet()) {
                logger.debug("process() delete game at: " + Calendar.getInstance().getTime());
                games.get(tableId).delete();
            }
        }

        logger.trace("process() finished at: " + Calendar.getInstance().getTime());
    }

}
