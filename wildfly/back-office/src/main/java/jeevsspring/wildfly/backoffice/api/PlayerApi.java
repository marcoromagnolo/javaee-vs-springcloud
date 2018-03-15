package jeevsspring.wildfly.backoffice.api;

import jeevsspring.wildfly.backoffice.api.json.*;
import jeevsspring.wildfly.backoffice.service.PlayerService;
import jeevsspring.wildfly.backoffice.service.ServiceException;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/player")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PlayerApi {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @Inject
    private PlayerService playerService;

    @GET
    @Path("/test")
    public Response test() {
        logger.trace("test()");
        Status out = new Status();
        out.setMessage("Test completed");
        return Response.ok(out).build();
    }

    @POST
    @Path("/wallet")
    public Response wallet(WalletIn in) {
        logger.trace("wallet(" + in + ")");

        Response response;
        WalletOut out = new WalletOut();
        try {
            out = playerService.wallet(in.getPlayerId(), in.getSessionId(), in.getSessionToken());
            response = Response.ok(out).build();
        } catch (ServiceException e) {
            logger.error(e);
            out.setError(true);
            out.setErrorCode(e.getErrorCode());
            response = Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        logger.debug("wallet(" + in + ") return " + out);
        return response;
    }

    @POST
    @Path("/account")
    public Response account(AccountIn in) {
        logger.trace("account(" + in + ")");

        Response response;
        AccountOut out = new AccountOut();
        try {
            out = playerService.account(in.getPlayerId(), in.getSessionId(), in.getSessionToken());
            response = Response.ok(out).build();
        } catch (ServiceException e) {
            logger.error(e);
            out.setError(true);
            out.setErrorCode(e.getErrorCode());
            response = Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        logger.debug("account(" + in + ") return " + out);
        return response;
    }

    @POST
    @Path("/login")
    public Response login(LoginIn in) {
        logger.trace("login(" + in + ")");

        Response response;
        LoginOut out = new LoginOut();
        try {
            out = playerService.login(in.getUsername(), in.getPassword());
            response = Response.ok(out).build();
        } catch (ServiceException e) {
            logger.error(e);
            out.setError(true);
            out.setErrorCode(e.getErrorCode());
            response = Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
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
            out = playerService.logout(in.getPlayerId(), in.getSessionId(), in.getSessionToken());
            response = Response.ok(out).build();
        } catch (ServiceException e) {
            logger.error(e);
            out.setError(true);
            out.setErrorCode(e.getErrorCode());
            response = Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        logger.debug("logout(" + in + ") return " + out);
        return response;
    }

    @POST
    @Path("/session-refresh")
    public Response sessionRefresh(SessionRefreshIn in) {
        logger.trace("sessionRefresh(" + in + ")");

        Response response;
        SessionRefreshOut out = new SessionRefreshOut();
        try {
            out = playerService.sessionRefresh(in.getPlayerId(), in.getSessionId(), in.getSessionToken());
            response = Response.ok(out).build();
        } catch (ServiceException e) {
            logger.error(e);
            out.setError(true);
            out.setErrorCode(e.getErrorCode());
            response = Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        logger.debug("sessionRefresh(" + in + ") return " + out);
        return response;
    }

    @POST
    @Path("/stake")
    public Response stake(StakeIn in) {
        logger.trace("stake(" + in + ")");

        Response response;
        StakeOut out = new StakeOut();
        try {
            out = playerService.stake(in.getPlayerId(), in.getSessionId(), in.getSessionToken(), in.getAmount());
            response = Response.ok(out).build();
        } catch (ServiceException e) {
            logger.error(e);
            out.setError(true);
            out.setErrorCode(e.getErrorCode());
            response = Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        logger.debug("stake(" + in + ") return " + out);
        return response;
    }

    @POST
    @Path("/win")
    public Response win(WinIn in) {
        logger.trace("win(" + in + ")");

        Response response;
        WinOut out = new WinOut();
        try {
            out = playerService.win(in.getPlayerId(), in.getSessionId(), in.getSessionToken(), in.getAmount());
            response = Response.ok(out).build();
        } catch (ServiceException e) {
            logger.error(e);
            out.setError(true);
            out.setErrorCode(e.getErrorCode());
            response = Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        logger.debug("win(" + in + ") return " + out);
        return response;
    }

    @POST
    @Path("/refund")
    public Response refund(RefundIn in) {
        logger.trace("refund(" + in + ")");

        Response response;
        RefundOut out = new RefundOut();
        try {
            out = playerService.refund(in.getPlayerId(), in.getSessionId(), in.getSessionToken(), in.getAmount());
            response = Response.ok(out).build();
        } catch (ServiceException e) {
            logger.error(e);
            out.setError(true);
            out.setErrorCode(e.getErrorCode());
            response = Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        logger.debug("refund(" + in + ") return " + out);
        return response;
    }
}
