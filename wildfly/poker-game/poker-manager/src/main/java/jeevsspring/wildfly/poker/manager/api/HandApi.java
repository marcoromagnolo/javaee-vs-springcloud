package jeevsspring.wildfly.poker.manager.api;

import jeevsspring.wildfly.poker.manager.api.json.hand.*;
import jeevsspring.wildfly.poker.manager.bo.BoClient;
import jeevsspring.wildfly.poker.manager.engine.player.PlayerActionType;
import jeevsspring.wildfly.poker.manager.engine.hand.Hands;
import jeevsspring.wildfly.poker.manager.lobby.Lobby;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@LocalBean
@Path("/hand")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HandApi {

    @EJB
    private BoClient boClient;

    @EJB
    private Hands hands;

    @EJB
    private Lobby lobby;

    @POST
    @Path("/bet")
    public BetOut bet(BetIn in) {
        BetOut out = new BetOut();
        String playerId = lobby.getPlayerId(in.getSessionId());
        hands.addHandAction(in.getHandId(), playerId, PlayerActionType.BET);
        return out;
    }

    @POST
    @Path("/call")
    public CallOut call(CallIn in) {
        CallOut out = new CallOut();
        String playerId = lobby.getPlayerId(in.getSessionId());
        hands.addHandAction(in.getHandId(), playerId, PlayerActionType.CALL);
        return out;
    }

    @POST
    @Path("/check")
    public CheckOut check(CheckIn in) {
        CheckOut out = new CheckOut();
        String playerId = lobby.getPlayerId(in.getSessionId());
        hands.addHandAction(in.getHandId(), playerId, PlayerActionType.CHECK);
        return out;
    }

    @POST
    @Path("/raise")
    public RaiseOut raise(RaiseIn in) {
        RaiseOut out = new RaiseOut();
        String playerId = lobby.getPlayerId(in.getSessionId());
        hands.addHandAction(in.getHandId(), playerId, PlayerActionType.RAISE, in.getAmount());
        return out;
    }

    @POST
    @Path("/fold")
    public FoldOut fold(FoldIn in) {
        FoldOut out = new FoldOut();
        String playerId = lobby.getPlayerId(in.getSessionId());
        hands.addHandAction(in.getHandId(), playerId, PlayerActionType.FOLD);
        return out;
    }

    @POST
    @Path("/sitin")
    public SitinOut sitin(SitinIn in) {
        SitinOut out = new SitinOut();
        String playerId = lobby.getPlayerId(in.getSessionId());
        hands.addHandAction(in.getHandId(), playerId, PlayerActionType.SIT_IN);
        return out;
    }

    @POST
    @Path("/sitout")
    public SitoutOut sitout(SitoutIn in) {
        SitoutOut out = new SitoutOut();
        String playerId = lobby.getPlayerId(in.getSessionId());
        hands.addHandAction(in.getHandId(), playerId, PlayerActionType.SIT_OUT);
        return out;
    }
}
