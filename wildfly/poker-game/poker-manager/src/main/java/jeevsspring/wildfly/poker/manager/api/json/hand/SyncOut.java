package jeevsspring.wildfly.poker.manager.api.json.hand;

import jeevsspring.wildfly.poker.manager.api.json.ActionOut;
import jeevsspring.wildfly.poker.manager.api.json.PlayerSessionOut;

import java.util.List;

/**
 * @author Marco Romagnolo
 */
public class SyncOut extends PlayerSessionOut {

    private List<ActionOut> actions;

    public List<ActionOut> getActions() {
        return actions;
    }

    public void setActions(List<ActionOut> actions) {
        this.actions = actions;
    }
}
