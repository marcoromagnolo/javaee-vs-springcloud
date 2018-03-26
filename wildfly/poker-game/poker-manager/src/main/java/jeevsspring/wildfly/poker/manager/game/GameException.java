package jeevsspring.wildfly.poker.manager.game;

public class GameException extends Exception {

    private final String error;

    public GameException(ErrorCode error) {
        super();
        this.error = error.name();
    }

    public GameException(ErrorCode error, String message) {
        super(message);
        this.error = error.name();
    }

    public String getError() {
        return error;
    }
}
