package jeevsspring.wildfly.poker.manager.game;

public enum ErrorCode {
    GAME_NOT_FOUND("Cannot find game id"),
    INVALID_SESSION("Invalid Session"),
    INTERNAL_SERVER_ERROR("Internal Server Error"),
    GAME_NOT_RUNNING("Game is not running");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
