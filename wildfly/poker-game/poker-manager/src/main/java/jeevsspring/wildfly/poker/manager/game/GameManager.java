package jeevsspring.wildfly.poker.manager.game;

import jeevsspring.wildfly.poker.manager.game.engine.Game;
import jeevsspring.wildfly.poker.manager.game.engine.GameAction;
import jeevsspring.wildfly.poker.manager.game.engine.GameActions;
import jeevsspring.wildfly.poker.manager.game.hand.HandActions;
import jeevsspring.wildfly.poker.manager.game.table.TableActionQueue;
import jeevsspring.wildfly.poker.manager.util.PMConfig;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import java.util.Calendar;

@Singleton
@Startup
public class GameManager<T extends Game<E>, E extends GameAction> {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @Resource
    TimerService timerService;

    @EJB
    private HandActions handActions;

    @EJB
    private TableActionQueue tableActions;

    @EJB
    private Games<T> games;

    @EJB
    private GameActions<E> gameActions;

    @EJB
    private PMConfig config;

    @PostConstruct
    public void init() {
        long time = config.getGameActionUpdateInterval() * 1000;
        timerService.createTimer(0, time, "Every " + time + " milliseconds");
    }

    @Timeout
    public void process(Timer timer) {
        logger.trace("process() timer info: " + timer.getInfo().toString());
        logger.trace("process() started at: " + Calendar.getInstance().getTime());

        // Poll Hand, Table & Game actions
        games.getAll().parallelStream()
                .forEach((T game) -> {
                    String tableId = game.getTableId();
                    try {

                        // Process Hand Actions (input)
                        if (handActions.contains(tableId)) {
                            game.actions(handActions.pop(tableId));
                        }

                        // Process Table Actions (input)
                        if (tableActions.contains(tableId)) {
                            game.actions(tableActions.pop(tableId));
                        }

                        // Process Game Actions (output)
                        if (!game.getGameActions().isEmpty()) {
                            gameActions.add(tableId, game.getGameActions());
                        }
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                        logger.warn("Game with tableId: " + tableId + " will be removed from active games");
                        try {
                            games.remove(tableId);
                        } catch (GameException e1) {
                            logger.error(e);
                        }
                    }
                });

        logger.trace("process() finished at: " + Calendar.getInstance().getTime());
    }

}
