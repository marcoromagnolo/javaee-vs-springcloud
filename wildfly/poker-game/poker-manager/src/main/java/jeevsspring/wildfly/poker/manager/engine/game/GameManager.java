package jeevsspring.wildfly.poker.manager.engine.game;

import jeevsspring.wildfly.poker.manager.engine.hand.HandActions;
import jeevsspring.wildfly.poker.manager.engine.table.TableActionQueue;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import java.util.Calendar;

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
    public void doWork(Timer timer) {
        logger.debug("GameManager :: doWork() timer info: " + timer.getInfo().toString());
        logger.debug("GameManager :: doWork() started at: " + Calendar.getInstance().getTime());

        // Poll Hand and Table actions made from players
        games.getAll().parallelStream()
                .forEach(game -> game.doActions(
                        handActions.get(game.getTableId()),
                        tableQueue.poll(game.getTableId())
                ));

        logger.debug("GameManager :: doWork() finished at: " + Calendar.getInstance().getTime());
    }

}
