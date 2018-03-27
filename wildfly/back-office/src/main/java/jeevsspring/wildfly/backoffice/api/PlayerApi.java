package jeevsspring.wildfly.backoffice.api;

import jeevsspring.wildfly.backoffice.api.json.*;
import jeevsspring.wildfly.backoffice.service.*;
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
        return Response.ok("Test completed").build();
    }

    @POST
    @Path("/wallet")
    public Response wallet(WalletIn in) {
        logger.trace("wallet(" + in + ")");

        Response response;
        try {
            WalletOut out = playerService.wallet(in.getPlayerId(), in.getSessionId(), in.getSessionToken());
            response = Response.ok(out).build();
        } catch (InvalidSessionException e) {
            logger.warn(e.getMessage());
            response = Response.serverError()
                    .entity(new Status(e.getErrorCode()))
                    .status(Response.Status.FORBIDDEN).build();
        }
        logger.debug("wallet(" + in + ") return " + response.getEntity());
        return response;
    }

    @POST
    @Path("/account")
    public Response account(AccountIn in) {
        logger.trace("account(" + in + ")");

        Response response;
        try {
            AccountOut out = playerService.account(in.getPlayerId(), in.getSessionId(), in.getSessionToken());
            response = Response.ok(out).build();
        } catch (InvalidSessionException e) {
            logger.warn(e.getMessage());
            response = Response.serverError()
                    .entity(new Status(e.getErrorCode()))
                    .status(Response.Status.FORBIDDEN).build();
        }
        logger.debug("account(" + in + ") return " + response.getEntity());
        return response;
    }

    @POST
    @Path("/login")
    public Response login(LoginIn in) {
        logger.trace("login(" + in + ")");

        Response response;
        try {
            LoginOut out = playerService.login(in.getUsername(), in.getPassword());
            response = Response.ok(out).build();
        } catch (AuthenticationException e) {
            logger.warn(e.getMessage());
            response = Response.serverError()
                    .entity(new Status(e.getErrorCode()))
                    .status(Response.Status.UNAUTHORIZED).build();
        } catch (InconsistentDataException e) {
            logger.error(e.getMessage());
            response = Response.serverError()
                    .entity(new Status(e.getErrorCode()))
                    .status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        logger.debug("login(" + in + ") return " + response.getEntity());
        return response;
    }

    @POST
    @Path("/logout")
    public Response logout(LogoutIn in) {
        logger.trace("logout(" + in + ")");

        Response response;
        try {
            LogoutOut out = playerService.logout(in.getPlayerId(), in.getSessionId(), in.getSessionToken());
            response = Response.ok(out).build();
        } catch (InvalidSessionException e) {
            logger.warn(e.getMessage());
            response = Response.serverError()
                    .entity(new Status(e.getErrorCode()))
                    .status(Response.Status.FORBIDDEN).build();
        }
        logger.debug("logout(" + in + ") return " + response.getEntity());
        return response;
    }

    @POST
    @Path("/session-refresh")
    public Response sessionRefresh(SessionRefreshIn in) {
        logger.trace("sessionRefresh(" + in + ")");

        Response response;
        try {
            SessionRefreshOut out = playerService.sessionRefresh(in.getPlayerId(), in.getSessionId(), in.getSessionToken());
            response = Response.ok(out).build();
        } catch (InvalidSessionException e) {
            logger.warn(e.getMessage());
            response = Response.serverError()
                    .entity(new Status(e.getErrorCode()))
                    .status(Response.Status.FORBIDDEN).build();
        }
        logger.debug("sessionRefresh(" + in + ") return " + response.getEntity());
        return response;
    }

    @POST
    @Path("/stake")
    public Response stake(StakeIn in) {
        logger.trace("stake(" + in + ")");

        Response response;
        try {
            StakeOut out = playerService.stake(in.getPlayerId(), in.getSessionId(), in.getSessionToken(), in.getAmount());
            response = Response.ok(out).build();
        } catch (InvalidSessionException e) {
            logger.warn(e.getMessage());
            response = Response.serverError()
                    .entity(new Status(e.getErrorCode()))
                    .status(Response.Status.FORBIDDEN).build();
        } catch (InvalidAmountException | InsufficientFundsException e) {
            logger.warn(e.getMessage());
            response = Response.serverError()
                    .entity(new Status(e.getErrorCode()))
                    .status(Response.Status.BAD_REQUEST).build();
        }
        logger.debug("stake(" + in + ") return " + response.getEntity());
        return response;
    }

    @POST
    @Path("/win")
    public Response win(WinIn in) {
        logger.trace("win(" + in + ")");

        Response response;
        try {
            WinOut out = playerService.win(in.getPlayerId(), in.getSessionId(), in.getSessionToken(), in.getAmount());
            response = Response.ok(out).build();
        } catch (InvalidSessionException e) {
            logger.warn(e.getMessage());
            response = Response.serverError()
                    .entity(new Status(e.getErrorCode()))
                    .status(Response.Status.FORBIDDEN).build();
        } catch (InvalidAmountException e) {
            logger.warn(e.getMessage());
            response = Response.serverError()
                    .entity(new Status(e.getErrorCode()))
                    .status(Response.Status.BAD_REQUEST).build();
        }
        logger.debug("win(" + in + ") return " + response.getEntity());
        return response;
    }

    @POST
    @Path("/refund")
    public Response refund(RefundIn in) {
        logger.trace("refund(" + in + ")");

        Response response;
        try {
            RefundOut out = playerService.refund(in.getPlayerId(), in.getSessionId(), in.getSessionToken(), in.getAmount());
            response = Response.ok(out).build();
        } catch (InvalidSessionException e) {
            logger.warn(e.getMessage());
            response = Response.serverError()
                    .entity(new Status(e.getErrorCode()))
                    .status(Response.Status.FORBIDDEN).build();
        } catch (InvalidAmountException e) {
            logger.warn(e.getMessage());
            response = Response.serverError()
                    .entity(new Status(e.getErrorCode()))
                    .status(Response.Status.BAD_REQUEST).build();
        }
        logger.debug("refund(" + in + ") return " + response.getEntity());
        return response;
    }
}
