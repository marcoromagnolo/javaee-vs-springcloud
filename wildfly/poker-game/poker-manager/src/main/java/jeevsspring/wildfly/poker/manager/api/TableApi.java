package jeevsspring.wildfly.poker.manager.api;

import jeevsspring.wildfly.poker.manager.api.json.lobby.EnterIn;
import jeevsspring.wildfly.poker.manager.api.json.lobby.EnterOut;
import jeevsspring.wildfly.poker.manager.api.json.lobby.QuitIn;
import jeevsspring.wildfly.poker.manager.api.json.lobby.QuitOut;
import jeevsspring.wildfly.poker.manager.api.json.table.BuyinIn;
import jeevsspring.wildfly.poker.manager.api.json.table.BuyinOut;
import jeevsspring.wildfly.poker.manager.api.json.table.BuyoutIn;
import jeevsspring.wildfly.poker.manager.api.json.table.BuyoutOut;
import jeevsspring.wildfly.poker.manager.engine.hand.Hands;
import jeevsspring.wildfly.poker.manager.engine.player.PlayerActionType;
import jeevsspring.wildfly.poker.manager.engine.table.Table;
import jeevsspring.wildfly.poker.manager.engine.table.TableActionType;
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
@Path("/table")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TableApi {

    @EJB
    private Lobby lobby;

    @POST
    @Path("/buyin")
    public BuyinOut buyin(BuyinIn in) {
        BuyinOut out = new BuyinOut();
        String playerId = lobby.getPlayerId(in.getSessionId());
        Table table = lobby.getTable(in.getTableId());
        table.addAction(TableActionType.BUY_IN, playerId, in.getAmount());
        out.setSessionId(in.getSessionId());
        out.setToken(in.getToken());
        return out;
    }

    @POST
    @Path("/buyout")
    public BuyoutOut buyout(BuyoutIn in) {
        BuyoutOut out = new BuyoutOut();
        String playerId = lobby.getPlayerId(in.getSessionId());
        Table table = lobby.getTable(in.getTableId());
        table.addAction(TableActionType.BUY_OUT, playerId);
        out.setSessionId(in.getSessionId());
        out.setToken(in.getToken());
        return out;
    }

    @POST
    @Path("/enter")
    public EnterOut enter(EnterIn in) {
        EnterOut out = new EnterOut();
        String playerId = lobby.getPlayerId(in.getSessionId());
        Table table = lobby.getTable(in.getTableId());
        table.addAction(TableActionType.BUY_OUT, playerId);
        out.setSessionId(in.getSessionId());
        out.setToken(in.getToken());
        return out;
    }

    @POST
    @Path("/quit")
    public QuitOut quit(QuitIn in) {
        QuitOut out = new QuitOut();
        String playerId = lobby.getPlayerId(in.getSessionId());
        Table table = lobby.getTable(in.getTableId());
        table.addAction(TableActionType.BUY_OUT, playerId);
        out.setSessionId(in.getSessionId());
        out.setToken(in.getToken());
        return out;
    }

}
