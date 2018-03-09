package jeevsspring.wildfly.poker.manager.api;

import jeevsspring.wildfly.poker.manager.api.json.ActionOut;
import jeevsspring.wildfly.poker.manager.api.json.Status;
import jeevsspring.wildfly.poker.manager.api.json.hand.*;
import jeevsspring.wildfly.poker.manager.exception.PMException;
import jeevsspring.wildfly.poker.manager.game.engine.Game;
import jeevsspring.wildfly.poker.manager.game.engine.GameAction;
import jeevsspring.wildfly.poker.manager.game.engine.GameQueue;
import jeevsspring.wildfly.poker.manager.game.engine.texasholdem.THGame;
import jeevsspring.wildfly.poker.manager.game.hand.Card;
import jeevsspring.wildfly.poker.manager.game.hand.HandActionType;
import jeevsspring.wildfly.poker.manager.game.hand.HandActions;
import jeevsspring.wildfly.poker.manager.game.hand.Pot;
import jeevsspring.wildfly.poker.manager.game.player.Player;
import jeevsspring.wildfly.poker.manager.lobby.LobbyPlayers;
import org.jboss.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Stateless
@LocalBean
@Path("/hand")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HandApi<E extends Game> {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @EJB
    private HandActions handQueue;

    @EJB
    private LobbyPlayers lobbyPlayers;

    @EJB
    private GameQueue<E> gameQueue;

    @GET
    @Path("/test")
    public Status test() {
        logger.trace("test()");
        Status out = new Status();
        out.setMessage("Test completed");
        return out;
    }

    @POST
    @Path("/bet")
    public BetOut bet(BetIn in) {
        logger.trace("bet(" + in + ")");
        BetOut out = new BetOut();
        String playerId = lobbyPlayers.getPlayerId(in.getSessionId());
        try {
            handQueue.insert(HandActionType.BET, in.getTableId(), in.getHandId(), playerId, in.getAmount());
            List<ActionOut> actions = toActions(in.getTableId(), playerId);
            out.setActions(actions);
            out.setSessionId(in.getSessionId());
            out.setToken(in.getToken());
        } catch (PMException e) {
            logger.error(e.getMessage(), e);
            out.setError(true);
            out.setErrorCode("GAME_NOT_STARTED");
        }
        logger.debug("bet(" + in + ") return " + out);
        return out;
    }

    @POST
    @Path("/call")
    public CallOut call(CallIn in) {
        logger.trace("call(" + in + ")");
        CallOut out = new CallOut();
        String playerId = lobbyPlayers.getPlayerId(in.getSessionId());
        try {
            handQueue.insert(HandActionType.CALL, in.getTableId(), in.getHandId(), playerId, null);
            List<ActionOut> actions = toActions(in.getTableId(), playerId);
            out.setActions(actions);
            out.setSessionId(in.getSessionId());
            out.setToken(in.getToken());
        } catch (PMException e) {
            logger.error(e.getMessage(), e);
            out.setError(true);
            out.setErrorCode("GAME_NOT_STARTED");
        }
        logger.debug("call(" + in + ") return " + out);
        return out;
    }

    @POST
    @Path("/check")
    public CheckOut check(CheckIn in) {
        logger.trace("check(" + in + ")");
        CheckOut out = new CheckOut();
        String playerId = lobbyPlayers.getPlayerId(in.getSessionId());
        try {
            handQueue.insert(HandActionType.CHECK, in.getTableId(), in.getHandId(), playerId, null);
            List<ActionOut> actions = toActions(in.getTableId(), playerId);
            out.setActions(actions);
            out.setSessionId(in.getSessionId());
            out.setToken(in.getToken());
        } catch (PMException e) {
            logger.error(e.getMessage(), e);
            out.setError(true);
            out.setErrorCode("GAME_NOT_STARTED");
        }
        logger.debug("check(" + in + ") return " + out);
        return out;
    }

    @POST
    @Path("/raise")
    public RaiseOut raise(RaiseIn in) {
        logger.trace("raise(" + in + ")");
        RaiseOut out = new RaiseOut();
        String playerId = lobbyPlayers.getPlayerId(in.getSessionId());
        try {
            handQueue.insert(HandActionType.RAISE, in.getTableId(), in.getHandId(), playerId, in.getAmount());
            List<ActionOut> actions = toActions(in.getTableId(), playerId);
            out.setActions(actions);
            out.setSessionId(in.getSessionId());
            out.setToken(in.getToken());
        } catch (PMException e) {
            logger.error(e.getMessage(), e);
            out.setError(true);
            out.setErrorCode("GAME_NOT_STARTED");
        }
        logger.debug("raise(" + in + ") return " + out);
        return out;
    }

    @POST
    @Path("/fold")
    public FoldOut fold(FoldIn in) {
        logger.trace("fold(" + in + ")");
        FoldOut out = new FoldOut();
        String playerId = lobbyPlayers.getPlayerId(in.getSessionId());
        try {
            handQueue.insert(HandActionType.FOLD, in.getTableId(), in.getHandId(), playerId, null);
            List<ActionOut> actions = toActions(in.getTableId(), playerId);
            out.setActions(actions);
            out.setSessionId(in.getSessionId());
            out.setToken(in.getToken());
        } catch (PMException e) {
            logger.error(e.getMessage(), e);
            out.setError(true);
            out.setErrorCode("GAME_NOT_STARTED");
        }
        logger.debug("fold(" + in + ") return " + out);
        return out;
    }

    @POST
    @Path("/sync")
    public SyncOut sync(SyncIn in) {
        logger.trace("sync(" + in + ")");
        SyncOut out = new SyncOut();
        String playerId = lobbyPlayers.getPlayerId(in.getSessionId());
        try {
            List<ActionOut> actions = toActions(in.getTableId(), playerId);
            out.setActions(actions);
            out.setSessionId(in.getSessionId());
            out.setToken(in.getToken());
        } catch (PMException e) {
            logger.error(e.getMessage(), e);
            out.setError(true);
            out.setErrorCode("GAME_NOT_STARTED");
        }
        logger.debug("sync(" + in + ") return " + out);
        return out;
    }

    private List<ActionOut> toActions(String tableId, String playerId) throws PMException {
        logger.trace("toActions(" + tableId + ", " + playerId + ")");
        List<ActionOut> out = new ArrayList<>();
        THGame game = (THGame) gameQueue.get(tableId);
        for (GameAction action : game.getQueue()) {
            boolean isVisitor = action.getVisitors().contains(playerId);
            ActionOut actionOut = newGameAction(action, playerId, isVisitor);
            actionOut.setTableId(tableId);
            out.add(actionOut);
        }
        return out;
    }

    private ActionOut newGameAction(GameAction o, String playerId, boolean isVisitor) {
        logger.trace("newGameAction(" + o + ", " + playerId + ", " + isVisitor + ")");
        ActionOut actionOut = new ActionOut();

        // Set mandatory params
        actionOut.setActionId(o.getId());
        actionOut.setHandId(o.getHandId());

        // Set Players (without player id)
        for (Player player : o.getPlayers().values()) {
            PlayerOut playerOut = new PlayerOut();

            //Set Player Cards only for current player, only if isn't a visitor
            if (!isVisitor && playerId.equals(player.getId())) {
                for (Card card : player.getCards()) {
                    String c = card.getSuit().getValue() + card.getSymbol().getValue();
                    CardOut cardOut = CardOut.valueOf(c);
                    playerOut.getCards().add(cardOut);
                }
            }
            playerOut.setBalance(player.getBalance());
            playerOut.setNickname(player.getNickname());
            playerOut.setSeat(player.getSeat());
            playerOut.setSitOut(player.isSitOut());
            actionOut.getPlayers().add(playerOut);
        }

        // Set Community Cards
        for (Card card : o.getCommunityCards()) {
            String c = card.getSuit().getValue() + card.getSymbol().getValue();
            CardOut cardOut = CardOut.valueOf(c);
            actionOut.getCommunityCards().add(cardOut);
        }

        // Set Pots (with nickname instead of player id)
        for (Pot pot : o.getPots()) {
            PotOut potOut = new PotOut();
            potOut.setValue(pot.getValue());
            for (String potPlayerId : pot.getPlayers()) {
                String nickname = o.getPlayers().get(potPlayerId).getNickname();
                potOut.getPlayers().add(nickname);
            }
            actionOut.getPots().add(potOut);
        }

        return actionOut;
    }
}
