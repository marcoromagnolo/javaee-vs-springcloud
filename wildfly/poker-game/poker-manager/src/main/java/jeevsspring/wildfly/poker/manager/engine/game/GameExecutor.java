package jeevsspring.wildfly.poker.manager.engine.game;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Marco Romagnolo
 */
public class GameExecutor {

    private static final int THREADS = 4;
    private static GameExecutor INSTANCE;
    private final ExecutorService pool;

    private GameExecutor() {
        pool = Executors.newCachedThreadPool();
    }

    public static void load() {
        if (INSTANCE == null) {
            INSTANCE = new GameExecutor();
        }
        for (int i = 0; i < THREADS; i++) {
            INSTANCE.pool.execute(new TexasHoldem());
        }
    }

}
