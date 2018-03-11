package jeevsspring.wildfly.poker.manager.lobby;

import jeevsspring.wildfly.poker.common.TableSettings;
import jeevsspring.wildfly.poker.manager.bo.BoClient;
import jeevsspring.wildfly.poker.manager.game.GameException;
import jeevsspring.wildfly.poker.manager.game.engine.Game;
import jeevsspring.wildfly.poker.manager.game.Games;
import jeevsspring.wildfly.poker.manager.game.engine.texasholdem.THGame;
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
    public void process(Timer timer) {
        logger.trace("process() timer info: " + timer.getInfo().toString());
        logger.trace("process() started at: " + Calendar.getInstance().getTime());

        // Check Queue and add game instances
        while (lobbyTables.createdExists()) {
            TableSettings settings = lobbyTables.pollCreated();
            THGame game = null;
            try {
                switch (settings.getGameType()) {

                    case TEXAS_HOLDEM:
                        logger.debug("process() create Texas Holdem Game at: " + Calendar.getInstance().getTime());
                        game = new THGame(settings);
                        break;

                    default:
                        logger.error("Game type unknown:  " + settings.getGameType());

                }
                games.add(game);
                logger.debug("process() New Game instance: " + game);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

        // Check and update tables games (async)
        while (lobbyTables.updatedExists()) {
            UpdatedTable updatedTable = lobbyTables.pollUpdated();
            logger.debug("process() update game at: " + Calendar.getInstance().getTime());
            try {
                games.get(updatedTable.getId()).update(updatedTable.getSettings());
            } catch (GameException e) {
                logger.error(e);
            }
        }

        // Check and delete tables games (async)
        while (lobbyTables.deletedExists()) {
            String tableId = lobbyTables.pollDeleted();
            logger.debug("process() delete game at: " + Calendar.getInstance().getTime());
            try {
                games.get(tableId).delete();
            } catch (GameException e) {
                logger.error(e);
            }
        }

        logger.trace("process() finished at: " + Calendar.getInstance().getTime());
    }

}
