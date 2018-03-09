package jeevsspring.wildfly.poker.manager.game;

import jeevsspring.wildfly.poker.manager.game.engine.Game;
import jeevsspring.wildfly.poker.manager.game.hand.HandAction;
import jeevsspring.wildfly.poker.manager.game.hand.HandActions;
import jeevsspring.wildfly.poker.manager.game.table.TableAction;
import jeevsspring.wildfly.poker.manager.game.table.TableActionQueue;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import java.util.Calendar;
import java.util.Map;
import java.util.Queue;

@Singleton
@Startup
public class GameManager {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @Resource
    TimerService timerService;

    @EJB
    private HandActions handActions;

    @EJB
    private TableActionQueue tableQueue;

    @EJB
    private Games<Game> games;

    @PostConstruct
    public void init() {
        timerService.createTimer(0, 1000, "Every seconds");
    }

    @Timeout
    public void process(Timer timer) {
        logger.trace("process() timer info: " + timer.getInfo().toString());
        logger.trace("process() started at: " + Calendar.getInstance().getTime());

        // Poll Hand and Table actions made from players
        games.getAll().parallelStream()
                .forEach(game -> {
                    String tableId = game.getTableId();
                    try {
                        if (handActions.contains(tableId)) {
                            game.actions(handActions.pop(tableId));
                        }
                        if (tableQueue.contains(tableId)) {
                            game.actions(tableQueue.pop(tableId));
                        }
                    } catch (Exception e) {
                        logger.warn("Game with tableId: " + tableId + " will be removed from active games");
                        games.remove(tableId);
                        logger.error(e.getMessage(), e);
                    }
                });

        logger.trace("process() finished at: " + Calendar.getInstance().getTime());
    }

}
