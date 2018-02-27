package jeevsspring.wildfly.poker.manager.api;

import jeevsspring.wildfly.poker.manager.api.json.Status;
import jeevsspring.wildfly.poker.manager.api.json.hand.SitinIn;
import jeevsspring.wildfly.poker.manager.api.json.hand.SitinOut;
import jeevsspring.wildfly.poker.manager.api.json.hand.SitoutIn;
import jeevsspring.wildfly.poker.manager.api.json.hand.SitoutOut;
import jeevsspring.wildfly.poker.manager.api.json.lobby.EnterIn;
import jeevsspring.wildfly.poker.manager.api.json.lobby.EnterOut;
import jeevsspring.wildfly.poker.manager.api.json.lobby.QuitIn;
import jeevsspring.wildfly.poker.manager.api.json.lobby.QuitOut;
import jeevsspring.wildfly.poker.manager.api.json.table.BuyinIn;
import jeevsspring.wildfly.poker.manager.api.json.table.BuyinOut;
import jeevsspring.wildfly.poker.manager.api.json.table.BuyoutIn;
import jeevsspring.wildfly.poker.manager.api.json.table.BuyoutOut;
import jeevsspring.wildfly.poker.manager.engine.game.Game;
import jeevsspring.wildfly.poker.manager.engine.game.Games;
import jeevsspring.wildfly.poker.manager.engine.table.TableActionQueue;
import jeevsspring.wildfly.poker.manager.engine.table.TableActionType;
import jeevsspring.wildfly.poker.manager.exception.PMException;
import jeevsspring.wildfly.poker.manager.lobby.LobbyPlayers;
import org.jboss.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Stateless
@LocalBean
@Path("/table")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TableApi {

    private final Logger logger = Logger.getLogger(getClass());

    @EJB
    private TableActionQueue tableQueue;

    @EJB
    private LobbyPlayers lobbyPlayers;

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
    @Path("/join")
    public EnterOut join(EnterIn in) {
        EnterOut out = new EnterOut();
        String playerId = lobbyPlayers.getPlayerId(in.getSessionId());
        Game game = games.get(in.getTableId());
        game.getVisitors().add(playerId);
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
        game.getVisitors().add(playerId);
        out.setSessionId(in.getSessionId());
        out.setToken(in.getToken());
        return out;
    }

    @POST
    @Path("/buyin")
    public BuyinOut buyin(BuyinIn in) {
        BuyinOut out = new BuyinOut();
        String playerId = lobbyPlayers.getPlayerId(in.getSessionId());
        try {
            tableQueue.insert(TableActionType.BUY_IN, in.getTableId(), playerId, in.getAmount());
            if (!games.get(in.getTableId()).isRunning()) throw new PMException();
            out.setSessionId(in.getSessionId());
            out.setToken(in.getToken());
        } catch (PMException e) {
            logger.error(e.getMessage(), e);
            out.setError(true);
            out.setErrorCode("GAME_NOT_STARTED");
        }
        return out;
    }

    @POST
    @Path("/buyout")
    public BuyoutOut buyout(BuyoutIn in) {
        BuyoutOut out = new BuyoutOut();
        String playerId = lobbyPlayers.getPlayerId(in.getSessionId());
        try {
            tableQueue.insert(TableActionType.BUY_OUT, in.getTableId(), playerId, null);
            if (!games.get(in.getTableId()).isRunning()) throw new PMException();
            out.setSessionId(in.getSessionId());
            out.setToken(in.getToken());
        } catch (PMException e) {
            logger.error(e.getMessage(), e);
            out.setError(true);
            out.setErrorCode("GAME_NOT_STARTED");
        }
        return out;
    }

    @POST
    @Path("/sitin")
    public SitinOut sitin(SitinIn in) {
        SitinOut out = new SitinOut();
        String playerId = lobbyPlayers.getPlayerId(in.getSessionId());
        try {
            tableQueue.insert(TableActionType.SIT_IN, in.getTableId(), playerId, in.getSeat());
            if (!games.get(in.getTableId()).isRunning()) throw new PMException();
            out.setSessionId(in.getSessionId());
            out.setToken(in.getToken());
        } catch (PMException e) {
            logger.error(e.getMessage(), e);
            out.setError(true);
            out.setErrorCode("GAME_NOT_STARTED");
        }
        return out;
    }

    @POST
    @Path("/sitout")
    public SitoutOut sitout(SitoutIn in) {
        SitoutOut out = new SitoutOut();
        String playerId = lobbyPlayers.getPlayerId(in.getSessionId());
        try {
            tableQueue.insert(TableActionType.SIT_OUT, in.getTableId(), playerId, null);
            if (!games.get(in.getTableId()).isRunning()) throw new PMException();
            out.setSessionId(in.getSessionId());
            out.setToken(in.getToken());
        } catch (PMException e) {
            logger.error(e.getMessage(), e);
            out.setError(true);
            out.setErrorCode("GAME_NOT_STARTED");
        }
        return out;
    }

}
