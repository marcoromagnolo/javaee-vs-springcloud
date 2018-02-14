package jeevsspring.wildfly.poker.manager.lobby;

/**
 * @author Marco Romagnolo
 */
public class LobbyAction {

    private LobbyActionType actionType;

    public LobbyAction(LobbyActionType actionType) {
        this.actionType = actionType;
    }
}
