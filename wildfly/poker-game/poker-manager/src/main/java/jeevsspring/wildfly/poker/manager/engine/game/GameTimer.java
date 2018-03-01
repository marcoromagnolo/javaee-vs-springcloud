package jeevsspring.wildfly.poker.manager.engine.game;

public class GameTimer {

    private long timer;
    private long limit;

    public GameTimer(long limit) {
        this.limit = limit;
        this.timer = System.currentTimeMillis();
    }

    public boolean isElapsed() {
        long now = System.currentTimeMillis();
        return (timer + limit) > now;
    }
}
