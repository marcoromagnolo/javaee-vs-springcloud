package jeevsspring.wildfly.poker.manager.api;

import jeevsspring.wildfly.poker.manager.api.json.hand.SitinIn;
import jeevsspring.wildfly.poker.manager.api.json.hand.SitinOut;
import jeevsspring.wildfly.poker.manager.api.json.hand.SitoutIn;
import jeevsspring.wildfly.poker.manager.api.json.hand.SitoutOut;
import jeevsspring.wildfly.poker.manager.api.json.table.*;
import jeevsspring.wildfly.poker.manager.bo.BOException;
import jeevsspring.wildfly.poker.manager.bo.json.BORefundOut;
import jeevsspring.wildfly.poker.manager.bo.json.BOStakeOut;
import jeevsspring.wildfly.poker.manager.game.BOTransactionManager;
import jeevsspring.wildfly.poker.manager.game.ErrorCode;
import jeevsspring.wildfly.poker.manager.game.GameException;
import jeevsspring.wildfly.poker.manager.game.Games;
import jeevsspring.wildfly.poker.manager.game.engine.Game;
import jeevsspring.wildfly.poker.manager.game.player.PlayerManager;
import jeevsspring.wildfly.poker.manager.game.table.TableActionQueue;
import org.jboss.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    private PlayerManager playerManager;

    @EJB
    private BOTransactionManager boTransactionManager;

    @EJB
    private Games games;

    @GET
    @Path("/test")
    public Response test() {
        logger.trace("test()");
        return Response.ok("Test completed").build();
    }

    @POST
    @Path("/join")
    public Response join(EnterIn in) {
        logger.trace("join(" + in + ")");

        Response response;
        EnterOut out = new EnterOut();
        try {
            String playerId = playerManager.getPlayerId(in.getSessionId());
            Game game = games.get(in.getTableId());
            game.getVisitors().add(playerId);
            out.setSessionId(in.getSessionId());
            out.setSessionToken(in.getToken());
            response = Response.ok(out).build();
        } catch (GameException e) {
            logger.error(e.getMessage(), e);
            out.setError(e.getError());
            response = Response.serverError()
                    .entity(out)
                    .status(Response.Status.FORBIDDEN).build();
        }
        logger.debug("join(" + in + ") return " + out);
        return response;
    }

    @POST
    @Path("/quit")
    public Response quit(QuitIn in) {
        logger.trace("quit(" + in + ")");

        Response response;
        QuitOut out = new QuitOut();
        try {
            String playerId = playerManager.getPlayerId(in.getSessionId());
            Game game = games.get(in.getTableId());
            game.getVisitors().remove(playerId);
            out.setSessionId(in.getSessionId());
            out.setSessionToken(in.getToken());
            response = Response.ok(out).build();
        } catch (GameException e) {
            logger.error(e.getMessage(), e);
            out.setError(e.getError());
            response = Response.serverError()
                    .entity(out)
                    .status(Response.Status.FORBIDDEN).build();
        }
        logger.debug("quit(" + in + ") return " + out);
        return response;
    }

    @POST
    @Path("/buyin")
    public Response buyin(BuyinIn in) {
        logger.trace("buyin(" + in + ")");

        Response response;
        BuyinOut out = new BuyinOut();
        try {
            String playerId = playerManager.getPlayerId(in.getSessionId());
            BOStakeOut bo = boTransactionManager.stake(in.getTableId(), playerId, Long.parseLong(in.getAmount()));
            out.setSessionId(bo.getSessionId());
            out.setSessionToken(bo.getSessionToken());
            out.setAmount(bo.getAmount());
            out.setBalance(bo.getBalance());
            response = Response.ok(out).build();
        } catch (BOException e) {
            logger.error(e.getMessage(), e);
            out.setError(e.getError());
            response = Response.serverError()
                    .entity(out)
                    .status(Response.Status.FORBIDDEN).build();
        } catch (GameException e) {
            logger.error(e.getMessage(), e);
            out.setError(e.getError());
            response = Response.serverError()
                    .entity(out)
                    .status(Response.Status.FORBIDDEN).build();
        }
        logger.debug("buyin(" + in + ") return " + out);
        return response;
    }

    @POST
    @Path("/buyout")
    public Response buyout(BuyoutIn in) {
        logger.trace("buyout(" + in + ")");

        Response response;
        BuyoutOut out = new BuyoutOut();
        try {
            String playerId = playerManager.getPlayerId(in.getSessionId());
            BORefundOut bo = boTransactionManager.refund(in.getTableId(), playerId);
            out.setSessionId(bo.getSessionId());
            out.setSessionToken(bo.getSessionToken());
            out.setAmount(bo.getAmount());
            out.setBalance(bo.getBalance());
            response = Response.ok(out).build();
        } catch (BOException e) {
            logger.error(e.getMessage(), e);
            out.setError(e.getError());
            response = Response.serverError()
                    .entity(out)
                    .status(Response.Status.FORBIDDEN).build();
        } catch (GameException e) {
            logger.error(e.getMessage(), e);
            out.setError(e.getError());
            response = Response.serverError()
                    .entity(out)
                    .status(Response.Status.FORBIDDEN).build();
        }
        logger.debug("buyout(" + in + ") return " + out);
        return response;
    }

    @POST
    @Path("/sitin")
    public Response sitin(SitinIn in) {
        logger.trace("sitin(" + in + ")");

        Response response;
        SitinOut out = new SitinOut();
        try {
            if (!games.get(in.getTableId()).isRunning()) throw new GameException(ErrorCode.GAME_NOT_RUNNING, "Game with tableId: " + in.getTableId() + " is not running");
            String playerId = playerManager.getPlayerId(in.getSessionId());
            tableQueue.sitin(in.getTableId(), playerId, Integer.parseInt(in.getSeat()));
            out.setSessionId(in.getSessionId());
            out.setSessionToken(in.getToken());
            response = Response.ok(out).build();
        } catch (GameException e) {
            logger.error(e.getMessage(), e);
            out.setError(e.getError());
            response = Response.serverError()
                    .entity(out)
                    .status(Response.Status.FORBIDDEN).build();
        }
        logger.debug("sitin(" + in + ") return " + out);
        return response;
    }

    @POST
    @Path("/sitout")
    public Response sitout(SitoutIn in) {
        logger.trace("sitout(" + in + ")");

        Response response;
        SitoutOut out = new SitoutOut();
        try {
            if (!games.get(in.getTableId()).isRunning()) throw new GameException(ErrorCode.GAME_NOT_RUNNING, "Game with tableId: " + in.getTableId() + " is not running");
            String playerId = playerManager.getPlayerId(in.getSessionId());
            tableQueue.sitout(in.getTableId(), playerId);
            out.setSessionId(in.getSessionId());
            out.setSessionToken(in.getToken());
            response = Response.ok(out).build();
        } catch (GameException e) {
            logger.error(e.getMessage(), e);
            out.setError(e.getError());
            response = Response.serverError()
                    .entity(out)
                    .status(Response.Status.FORBIDDEN).build();
        }
        logger.debug("sitout(" + in + ") return " + out);
        return response;
    }

}
