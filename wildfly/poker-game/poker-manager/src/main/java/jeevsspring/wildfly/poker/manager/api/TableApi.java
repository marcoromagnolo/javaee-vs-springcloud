package jeevsspring.wildfly.poker.manager.api;

import jeevsspring.wildfly.poker.manager.api.json.lobby.EnterIn;
import jeevsspring.wildfly.poker.manager.api.json.lobby.EnterOut;
import jeevsspring.wildfly.poker.manager.api.json.lobby.QuitIn;
import jeevsspring.wildfly.poker.manager.api.json.lobby.QuitOut;
import jeevsspring.wildfly.poker.manager.api.json.table.BuyinIn;
import jeevsspring.wildfly.poker.manager.api.json.table.BuyinOut;
import jeevsspring.wildfly.poker.manager.api.json.table.BuyoutIn;
import jeevsspring.wildfly.poker.manager.api.json.table.BuyoutOut;
import jeevsspring.wildfly.poker.manager.engine.game.GameActions;
import jeevsspring.wildfly.poker.manager.engine.game.Games;
import jeevsspring.wildfly.poker.manager.engine.table.TableActionQueue;
import jeevsspring.wildfly.poker.manager.engine.table.TableActionType;
import jeevsspring.wildfly.poker.manager.lobby.LobbyPlayers;

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
@Path("/table")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TableApi {

    @EJB
    private TableActionQueue tableQueue;

    @EJB
    private LobbyPlayers lobbyPlayers;

    @EJB
    private GameActions gameActions;

    @POST
    @Path("/buyin")
    public BuyinOut buyin(BuyinIn in) {
        BuyinOut out = new BuyinOut();
        String playerId = lobbyPlayers.getPlayerId(in.getSessionId());
        tableQueue.insert(TableActionType.BUY_IN, in.getTableId(), playerId, in.getAmount());
        // TODO add Sync
        out.setSessionId(in.getSessionId());
        out.setToken(in.getToken());
        return out;
    }

    @POST
    @Path("/buyout")
    public BuyoutOut buyout(BuyoutIn in) {
        BuyoutOut out = new BuyoutOut();
        String playerId = lobbyPlayers.getPlayerId(in.getSessionId());
        tableQueue.insert(TableActionType.BUY_OUT, in.getTableId(), playerId, null);
        // TODO add Sync
        out.setSessionId(in.getSessionId());
        out.setToken(in.getToken());
        return out;
    }

}
