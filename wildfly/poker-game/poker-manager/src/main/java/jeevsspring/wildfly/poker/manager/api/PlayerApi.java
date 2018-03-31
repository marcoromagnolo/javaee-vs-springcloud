package jeevsspring.wildfly.poker.manager.api;

import jeevsspring.wildfly.poker.manager.api.json.player.*;
import jeevsspring.wildfly.poker.manager.bo.BOException;
import jeevsspring.wildfly.poker.manager.bo.json.BOAccountOut;
import jeevsspring.wildfly.poker.manager.bo.json.BOLoginOut;
import jeevsspring.wildfly.poker.manager.bo.json.BOLogoutOut;
import jeevsspring.wildfly.poker.manager.bo.json.BOWalletOut;
import jeevsspring.wildfly.poker.manager.game.player.PlayerManager;
import org.jboss.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@LocalBean
@Path("/player")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PlayerApi {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @EJB
    private PlayerManager playerManager;

    @GET
    @Path("/test")
    public Response test() {
        logger.trace("test()");
        return Response.ok("Test completed").build();
    }

    @POST
    @Path("/wallet")
    public Response wallet(WalletIn in) {
        logger.trace("wallet(" + in + ")");

        Response response;
        WalletOut out = new WalletOut();
        try {
            BOWalletOut bo = playerManager.getWallet(in.getSessionId(), in.getSessionToken());
            out.setBalance(bo.getBalance());
            response = Response.ok(out).build();
        } catch (BOException e) {
            logger.error(e);
            out.setError(e.getError());
            response = Response.serverError()
                    .entity(out)
                    .status(Response.Status.FORBIDDEN).build();
        }
        logger.debug("wallet(" + in + ") return " + out);
        return response;
    }

    @POST
    @Path("/account")
    public Response account(AccountIn in) {
        logger.trace("wallet(" + in + ")");

        Response response;
        AccountOut out = new AccountOut();
        try {
            BOAccountOut bo = playerManager.getAccount(in.getSessionId(), in.getSessionToken());
            out.setFirstName(bo.getFirstName());
            out.setLastName(bo.getLastName());
            response = Response.ok(out).build();
        } catch (BOException e) {
            logger.error(e);
            out.setError(e.getError());
            response = Response.serverError()
                    .entity(out)
                    .status(Response.Status.FORBIDDEN).build();
        }
        logger.debug("login(" + in + ") return " + out);
        return response;
    }

    @POST
    @Path("/login")
    public Response login(LoginIn in) {
        logger.trace("login(" + in + ")");

        Response response;
        LoginOut out = new LoginOut();
        try {
            BOLoginOut bo = playerManager.login(in.getUsername(), in.getPassword());
            out.setSessionId(bo.getSessionId());
            out.setSessionToken(bo.getSessionToken());
            out.setNickname(bo.getNickname());
            out.setSessionCreateTime(bo.getSessionCreateTime());
            out.setSessionExpireTime(bo.getSessionCreateTime());

            // Set Account
            AccountOut account = new AccountOut();
            account.setFirstName(bo.getFirstName());
            account.setLastName(bo.getLastName());
            out.setAccount(account);

            // Set Wallet
            WalletOut wallet = new WalletOut();
            wallet.setBalance(bo.getBalance());
            out.setWallet(wallet);

            response = Response.ok(out).build();
        } catch (BOException e) {
            logger.error(e);
            out.setError(e.getError());
            response = Response.serverError()
                    .entity(out)
                    .status(Response.Status.FORBIDDEN).build();
        }
        logger.debug("login(" + in + ") return " + out);
        return response;
    }

    @POST
    @Path("/logout")
    public Response logout(LogoutIn in) {
        logger.trace("logout(" + in + ")");

        Response response;
        LogoutOut out = new LogoutOut();
        try {
            BOLogoutOut bo = playerManager.logout(in.getSessionId(), in.getSessionToken());
            out.setMessage(bo.getMessage());
            response = Response.ok(out).build();
        } catch (BOException e) {
            logger.error(e);
            out.setError(e.getError());
            response = Response.serverError()
                    .entity(out)
                    .status(Response.Status.FORBIDDEN).build();
        }
        logger.debug("logout(" + in + ") return " + out);
        return response;
    }
}
