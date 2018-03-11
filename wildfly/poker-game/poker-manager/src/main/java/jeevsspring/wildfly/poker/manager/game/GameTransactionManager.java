package jeevsspring.wildfly.poker.manager.game;

import jeevsspring.wildfly.poker.manager.bo.BOException;
import jeevsspring.wildfly.poker.manager.bo.BoClient;
import jeevsspring.wildfly.poker.manager.bo.json.BORefundIn;
import jeevsspring.wildfly.poker.manager.bo.json.BORefundOut;
import jeevsspring.wildfly.poker.manager.bo.json.BOStakeIn;
import jeevsspring.wildfly.poker.manager.bo.json.BOWinIn;
import jeevsspring.wildfly.poker.manager.game.engine.Game;
import jeevsspring.wildfly.poker.manager.game.engine.GameAction;
import jeevsspring.wildfly.poker.manager.game.engine.GameActions;
import jeevsspring.wildfly.poker.manager.game.player.Player;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import java.util.Calendar;
import java.util.Map;
import java.util.Queue;

@Singleton
@Startup
public class GameTransactionManager<E extends GameAction> {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @Resource
    TimerService timerService;

    @EJB
    private BoClient boClient;

    @EJB
    private GameActions<E> gameActions;

    @EJB
    private Games games;

    @PostConstruct
    public void init() {
        timerService.createTimer(0, 5000, "Every 5 seconds");
    }

    @Timeout
    public void process(Timer timer) {
        logger.trace("process() timer info: " + timer.getInfo().toString());
        logger.trace("process() started at: " + Calendar.getInstance().getTime());
        gameActions.getAll().parallelStream()
                .forEach((Map.Entry<String, Queue<E>> entry) -> {
                    String tableId = entry.getKey();
                    for (E gameAction : entry.getValue()) {
                        if (gameAction.getWinnings() != null) {
                            reward(gameAction.getWinnings());
                        }
                    }
                });

        logger.trace("process() finished at: " + Calendar.getInstance().getTime());
    }

    private void reward(Map<String, Long> rewards) {
        for (Map.Entry<String, Long> reward : rewards.entrySet()) {
            BOWinIn in = new BOWinIn();
            in.setPlayerId(reward.getKey());
            in.setAmount(reward.getValue());
            try {
                boClient.win(in);
            } catch (BOException e) {
                logger.error(e);
            }
        }
    }

    public void stake(String tableId, String playerId, long amount) throws GameException {
        if (!games.get(tableId).isRunning()) throw new GameException("Game with tableId: " + tableId + " is not running");
        BOStakeIn in = new BOStakeIn();
        in.setPlayerId(playerId);
        in.setAmount(amount);
        try {
            boClient.stake(in);
        } catch (BOException e) {
            logger.error(e);
            throw new GameException("REFUND_ERROR");
        }
    }

    public long refund(String tableId, String playerId) throws GameException {
        if (!games.get(tableId).isRunning()) throw new GameException("Game with tableId: " + tableId + " is not running");
        Game game = games.get(tableId);
        Player player = game.getPlayer(playerId);
        if (player.getBalance() > 0) {
            BORefundIn in = new BORefundIn();
            in.setPlayerId(playerId);
            in.setAmount(player.getBalance());
            try {
                BORefundOut boRefundOut = boClient.refund(in);
                return boRefundOut.getAmount();
            } catch (BOException e) {
                logger.error(e);
                throw new GameException("REFUND_ERROR");
            }
        }
        return 0;
    }
}
