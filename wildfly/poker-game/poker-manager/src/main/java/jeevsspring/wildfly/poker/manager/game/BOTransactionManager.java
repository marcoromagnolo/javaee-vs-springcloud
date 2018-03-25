package jeevsspring.wildfly.poker.manager.game;

import jeevsspring.wildfly.poker.manager.bo.BOException;
import jeevsspring.wildfly.poker.manager.bo.BOClient;
import jeevsspring.wildfly.poker.manager.bo.json.*;
import jeevsspring.wildfly.poker.manager.game.engine.Game;
import jeevsspring.wildfly.poker.manager.game.engine.GameAction;
import jeevsspring.wildfly.poker.manager.game.engine.GameActions;
import jeevsspring.wildfly.poker.manager.game.player.Player;
import jeevsspring.wildfly.poker.manager.util.PMConfig;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import java.util.Calendar;
import java.util.Map;
import java.util.Queue;

@Singleton
@Startup
public class BOTransactionManager<E extends GameAction> {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @Resource
    TimerService timerService;

    @EJB
    private BOClient boClient;

    @EJB
    private GameActions<E> gameActions;

    @EJB
    private Games games;

    @EJB
    private SystemSessionManager systemSession;

    @EJB
    private PMConfig config;

    @PostConstruct
    public void init() {
        long time = config.getBoTransactionUpdateInterval() * 1000;
        timerService.createTimer(0, time, "Every " + time + " milliseconds");
    }

    @Lock(value = LockType.READ)
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
        if (rewards != null) {
            for (Map.Entry<String, Long> reward : rewards.entrySet()) {
                BOWinIn in = new BOWinIn();
                in.setPlayerId(reward.getKey());
                in.setAmount(reward.getValue());
                in.setSessionId(systemSession.getSessionId());
                in.setSessionToken(systemSession.getSessionToken());
                try {
                    boClient.win(in);
                } catch (BOException e) {
                    logger.error(e);
                }
            }
        }
    }

    public BOStakeOut stake(String tableId, String playerId, long amount) throws GameException {
        if (!games.get(tableId).isRunning()) throw new GameException("Game with tableId: " + tableId + " is not running");
        BOStakeIn in = new BOStakeIn();
        in.setPlayerId(playerId);
        in.setSessionId(systemSession.getSessionId());
        in.setSessionToken(systemSession.getSessionToken());
        in.setAmount(amount);
        try {
            BOStakeOut out = boClient.stake(in);
            Game game = games.get(tableId);
            Player player = new Player(out.getPlayerId(), out.getNickname(), amount);
            game.getPlayers().put(out.getPlayerId(), player);
            return out;
        } catch (BOException e) {
            logger.error(e);
            throw new GameException("REFUND_ERROR");
        }
    }

    public BORefundOut refund(String tableId, String playerId) throws GameException {
        if (!games.get(tableId).isRunning()) throw new GameException("Game with tableId: " + tableId + " is not running");
        Game game = games.get(tableId);
        Player player = game.getPlayer(playerId);
        BORefundIn in = new BORefundIn();
        in.setPlayerId(playerId);
        in.setAmount(player.getBalance());
        in.setSessionId(systemSession.getSessionId());
        in.setSessionToken(systemSession.getSessionToken());
        try {
            BORefundOut out = boClient.refund(in);
            return out;
        } catch (BOException e) {
            logger.error(e);
            throw new GameException("REFUND_ERROR");
        }
    }
}
