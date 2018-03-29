package jeevsspring.wildfly.poker.manager.api.json.table;

import jeevsspring.wildfly.poker.manager.api.json.hand.ActionOut;
import jeevsspring.wildfly.poker.manager.api.json.player.PlayerSessionOut;

import java.util.List;

public class EnterOut extends PlayerSessionOut {

    private List<ActionOut> actions;

    public List<ActionOut> getActions() {
        return actions;
    }

    public void setActions(List<ActionOut> actions) {
        this.actions = actions;
    }

    @Override
    public String toString() {
        return "EnterOut{" +
                "actions=" + actions +
                "} " + super.toString();
    }
}
