package jeevsspring.wildfly.poker.manager.api;

import jeevsspring.wildfly.poker.manager.api.json.ActionOut;
import jeevsspring.wildfly.poker.manager.api.json.Status;
import jeevsspring.wildfly.poker.manager.api.json.lobby.EnterIn;
import jeevsspring.wildfly.poker.manager.api.json.lobby.EnterOut;
import jeevsspring.wildfly.poker.manager.api.json.lobby.QuitIn;
import jeevsspring.wildfly.poker.manager.api.json.lobby.QuitOut;
import jeevsspring.wildfly.poker.manager.api.json.table.BuyinIn;
import jeevsspring.wildfly.poker.manager.api.json.table.BuyinOut;
import jeevsspring.wildfly.poker.manager.api.json.table.BuyoutIn;
import jeevsspring.wildfly.poker.manager.api.json.table.BuyoutOut;
import jeevsspring.wildfly.poker.manager.engine.game.Game;
import jeevsspring.wildfly.poker.manager.engine.game.GameAction;
import jeevsspring.wildfly.poker.manager.engine.game.GameActions;
import jeevsspring.wildfly.poker.manager.engine.game.Games;
import jeevsspring.wildfly.poker.manager.engine.table.TableAction;
import jeevsspring.wildfly.poker.manager.engine.table.TableActionQueue;
import jeevsspring.wildfly.poker.manager.engine.table.TableActionType;
import jeevsspring.wildfly.poker.manager.lobby.LobbyPlayers;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Queue;

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

    @EJB
    private Games games;

    @GET
    @Path("/test")
    public Status test() {
        Status out = new Status();
        out.setMessage("Test completed");
        return out;
    }

    @POST
    @Path("/buyin")
    public BuyinOut buyin(BuyinIn in) {
        BuyinOut out = new BuyinOut();
        String playerId = lobbyPlayers.getPlayerId(in.getSessionId());
        tableQueue.insert(TableActionType.BUY_IN, in.getTableId(), playerId, in.getAmount());
        Queue<GameAction> queue = gameActions.get(in.getTableId(), playerId);
        List<ActionOut> actions = HandApi.toActions(queue);
        out.setActions(actions);
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

    @POST
    @Path("/join")
    public EnterOut join(EnterIn in) {
        EnterOut out = new EnterOut();
        String playerId = lobbyPlayers.getPlayerId(in.getSessionId());
        Game game = games.get(in.getTableId());
        game.action(new TableAction(TableActionType.ENTER, in.getTableId(), playerId, null));
        Queue<GameAction> queue = gameActions.get(in.getTableId(), playerId);
        List<ActionOut> actions = HandApi.toActions(queue);
        out.setActions(actions);
        out.setSessionId(in.getSessionId());
        out.setToken(in.getToken());
        return out;
    }

    @POST
    @Path("/quit")
    public QuitOut quit(QuitIn in) {
        QuitOut out = new QuitOut();
        String playerId = lobbyPlayers.getPlayerId(in.getSessionId());
        Game game = games.get(in.getTableId());
        game.action(new TableAction(TableActionType.QUIT, in.getTableId(), playerId, null));
        out.setSessionId(in.getSessionId());
        out.setToken(in.getToken());
        return out;
    }


}
