package jeevsspring.wildfly.poker.manager.api;

import jeevsspring.wildfly.poker.manager.api.json.Status;
import jeevsspring.wildfly.poker.manager.api.json.hand.SitinIn;
import jeevsspring.wildfly.poker.manager.api.json.hand.SitinOut;
import jeevsspring.wildfly.poker.manager.api.json.hand.SitoutIn;
import jeevsspring.wildfly.poker.manager.api.json.hand.SitoutOut;
import jeevsspring.wildfly.poker.manager.api.json.table.EnterIn;
import jeevsspring.wildfly.poker.manager.api.json.table.EnterOut;
import jeevsspring.wildfly.poker.manager.api.json.table.QuitIn;
import jeevsspring.wildfly.poker.manager.api.json.table.QuitOut;
import jeevsspring.wildfly.poker.manager.api.json.table.BuyinIn;
import jeevsspring.wildfly.poker.manager.api.json.table.BuyinOut;
import jeevsspring.wildfly.poker.manager.api.json.table.BuyoutIn;
import jeevsspring.wildfly.poker.manager.api.json.table.BuyoutOut;
import jeevsspring.wildfly.poker.manager.bo.BOException;
import jeevsspring.wildfly.poker.manager.bo.json.BORefundOut;
import jeevsspring.wildfly.poker.manager.bo.json.BOStakeOut;
import jeevsspring.wildfly.poker.manager.game.GameException;
import jeevsspring.wildfly.poker.manager.game.BOTransactionManager;
import jeevsspring.wildfly.poker.manager.game.engine.Game;
import jeevsspring.wildfly.poker.manager.game.Games;
import jeevsspring.wildfly.poker.manager.game.table.TableActionQueue;
import jeevsspring.wildfly.poker.manager.game.player.PlayerManager;
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
    private PlayerManager playerManager;

    @EJB
    private BOTransactionManager boTransactionManager;

    @EJB
    private Games games;

    @GET
    @Path("/test")
    public Status test() {
        logger.trace("test()");
        Status out = new Status();
        out.setMessage("Test completed");
        return out;
    }

    @POST
    @Path("/join")
    public EnterOut join(EnterIn in) {
        logger.trace("join(" + in + ")");
        EnterOut out = new EnterOut();
        try {
            String playerId = playerManager.getPlayerId(in.getSessionId());
            Game game = games.get(in.getTableId());
            game.getVisitors().add(playerId);
            out.setSessionId(in.getSessionId());
            out.setSessionToken(in.getToken());
        } catch (BOException | GameException e) {
            logger.error(e.getMessage());
            out.setError(true);
            out.setErrorCode("GAME_NOT_STARTED");
        }
        logger.debug("join(" + in + ") return " + out);
        return out;
    }

    @POST
    @Path("/quit")
    public QuitOut quit(QuitIn in) {
        logger.trace("quit(" + in + ")");
        QuitOut out = new QuitOut();
        try {
            String playerId = playerManager.getPlayerId(in.getSessionId());
            Game game = games.get(in.getTableId());
            game.getVisitors().remove(playerId);
            out.setSessionId(in.getSessionId());
            out.setSessionToken(in.getToken());
        } catch (BOException | GameException e) {
            logger.error(e.getMessage());
            out.setError(true);
            out.setErrorCode("GAME_NOT_STARTED");
        }
        logger.debug("quit(" + in + ") return " + out);
        return out;
    }

    @POST
    @Path("/buyin")
    public BuyinOut buyin(BuyinIn in) {
        logger.trace("buyin(" + in + ")");
        BuyinOut out = new BuyinOut();
        try {
            String playerId = playerManager.getPlayerId(in.getSessionId());
            BOStakeOut bo = boTransactionManager.stake(in.getTableId(), playerId, Long.parseLong(in.getAmount()));
            out.setSessionId(bo.getSessionId());
            out.setSessionToken(bo.getSessionToken());
            out.setAmount(bo.getAmount());
            out.setBalance(bo.getBalance());
        } catch (BOException | GameException e) {
            logger.error(e.getMessage(), e);
            out.setError(true);
            out.setErrorCode("GAME_NOT_STARTED");
        }
        logger.debug("buyin(" + in + ") return " + out);
        return out;
    }

    @POST
    @Path("/buyout")
    public BuyoutOut buyout(BuyoutIn in) {
        logger.trace("buyout(" + in + ")");
        BuyoutOut out = new BuyoutOut();
        try {
            String playerId = playerManager.getPlayerId(in.getSessionId());
            BORefundOut bo = boTransactionManager.refund(in.getTableId(), playerId);
            if (bo != null) {
                out.setSessionId(bo.getSessionId());
                out.setSessionToken(bo.getSessionToken());
                out.setAmount(bo.getAmount());
                out.setBalance(bo.getBalance());
            }
        } catch (BOException | GameException e) {
            logger.error(e.getMessage(), e);
            out.setError(true);
            out.setErrorCode("GAME_NOT_STARTED");
        }
        logger.debug("buyout(" + in + ") return " + out);
        return out;
    }

    @POST
    @Path("/sitin")
    public SitinOut sitin(SitinIn in) {
        logger.trace("sitin(" + in + ")");
        SitinOut out = new SitinOut();
        try {
            if (!games.get(in.getTableId()).isRunning()) throw new GameException("Game with tableId: " + in.getTableId() + " is not running");
            String playerId = playerManager.getPlayerId(in.getSessionId());
            tableQueue.sitin(in.getTableId(), playerId, Integer.parseInt(in.getSeat()));
            out.setSessionId(in.getSessionId());
            out.setSessionToken(in.getToken());
        } catch (BOException | GameException e) {
            logger.error(e.getMessage(), e);
            out.setError(true);
            out.setErrorCode("GAME_NOT_STARTED");
        }
        logger.debug("sitin(" + in + ") return " + out);
        return out;
    }

    @POST
    @Path("/sitout")
    public SitoutOut sitout(SitoutIn in) {
        logger.trace("sitout(" + in + ")");
        SitoutOut out = new SitoutOut();
        try {
            if (!games.get(in.getTableId()).isRunning()) throw new GameException("Game with tableId: " + in.getTableId() + " is not running");
            String playerId = playerManager.getPlayerId(in.getSessionId());
            tableQueue.sitout(in.getTableId(), playerId);
            out.setSessionId(in.getSessionId());
            out.setSessionToken(in.getToken());
        } catch (BOException | GameException e) {
            logger.error(e.getMessage(), e);
            out.setError(true);
            out.setErrorCode("GAME_NOT_STARTED");
        }
        logger.debug("sitout(" + in + ") return " + out);
        return out;
    }

}
