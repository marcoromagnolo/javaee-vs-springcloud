package jeevsspring.wildfly.poker.manager.api;

import jeevsspring.wildfly.poker.manager.api.json.hand.*;
import jeevsspring.wildfly.poker.manager.bo.BoClient;
import jeevsspring.wildfly.poker.manager.engine.game.GameAction;
import jeevsspring.wildfly.poker.manager.engine.game.GameActions;
import jeevsspring.wildfly.poker.manager.engine.game.Games;
import jeevsspring.wildfly.poker.manager.engine.hand.HandActionQueue;
import jeevsspring.wildfly.poker.manager.engine.hand.HandActionType;
import jeevsspring.wildfly.poker.manager.lobby.LobbyPlayers;
import jeevsspring.wildfly.poker.manager.lobby.LobbyTables;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Queue;

@Stateless
@LocalBean
@Path("/hand")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HandApi {

    @EJB
    private BoClient boClient;

    @EJB
    private HandActionQueue handQueue;

    @EJB
    private LobbyPlayers lobbyPlayers;

    @EJB
    private GameActions gameActions;


    @POST
    @Path("/bet")
    public BetOut bet(BetIn in) {
        BetOut out = new BetOut();
        String playerId = lobbyPlayers.getPlayerId(in.getSessionId());
        handQueue.insert(HandActionType.BET, in.getTableId(), in.getHandId(), playerId, in.getAmount());
        return out;
    }

    @POST
    @Path("/call")
    public CallOut call(CallIn in) {
        CallOut out = new CallOut();
        String playerId = lobbyPlayers.getPlayerId(in.getSessionId());
        handQueue.insert(HandActionType.CALL, in.getTableId(), in.getHandId(), playerId, null);
        return out;
    }

    @POST
    @Path("/check")
    public CheckOut check(CheckIn in) {
        CheckOut out = new CheckOut();
        String playerId = lobbyPlayers.getPlayerId(in.getSessionId());
        handQueue.insert(HandActionType.CHECK, in.getTableId(), in.getHandId(), playerId, null);
        return out;
    }

    @POST
    @Path("/raise")
    public RaiseOut raise(RaiseIn in) {
        RaiseOut out = new RaiseOut();
        String playerId = lobbyPlayers.getPlayerId(in.getSessionId());
        handQueue.insert(HandActionType.RAISE, in.getTableId(), in.getHandId(), playerId, in.getAmount());
        return out;
    }

    @POST
    @Path("/fold")
    public FoldOut fold(FoldIn in) {
        FoldOut out = new FoldOut();
        String playerId = lobbyPlayers.getPlayerId(in.getSessionId());
        handQueue.insert(HandActionType.FOLD, in.getTableId(), in.getHandId(), playerId, null);
        return out;
    }

    @POST
    @Path("/sitin")
    public SitinOut sitin(SitinIn in) {
        SitinOut out = new SitinOut();
        String playerId = lobbyPlayers.getPlayerId(in.getSessionId());
        handQueue.insert(HandActionType.SIT_IN, in.getTableId(), in.getHandId(), playerId, null);
        return out;
    }

    @POST
    @Path("/sitout")
    public SitoutOut sitout(SitoutIn in) {
        SitoutOut out = new SitoutOut();
        String playerId = lobbyPlayers.getPlayerId(in.getSessionId());
        handQueue.insert(HandActionType.SIT_OUT, in.getTableId(), in.getHandId(), playerId, null);
        return out;
    }

    @POST
    @Path("/sync")
    public SyncOut sync(SyncIn in) {
        SyncOut out = new SyncOut();
        String playerId = lobbyPlayers.getPlayerId(in.getSessionId());
        Queue<GameAction> queue = gameActions.get(in.getTableId(), playerId);
        // TODO return out json with player data
        return out;
    }
}
