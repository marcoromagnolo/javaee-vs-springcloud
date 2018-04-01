package jeevsspring.spring.poker.manager.player.bo;

public class BOException extends Exception {

    private final String error;

    public BOException(String error) {
        super();
        this.error = error;
    }

    public BOException(String error, String message) {
        super(message);
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
