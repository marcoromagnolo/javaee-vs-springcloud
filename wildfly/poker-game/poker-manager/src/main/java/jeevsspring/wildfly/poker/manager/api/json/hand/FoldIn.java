package jeevsspring.wildfly.poker.manager.api.json.hand;

import jeevsspring.wildfly.poker.manager.api.json.lobby.PlayerSessionIn;

public class FoldIn extends PlayerSessionIn {

    private String handId;

    public String getHandId() {
        return handId;
    }

    public void setHandId(String handId) {
        this.handId = handId;
    }
}
