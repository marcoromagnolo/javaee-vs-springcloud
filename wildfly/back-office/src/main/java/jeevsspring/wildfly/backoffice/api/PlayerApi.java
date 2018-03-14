package jeevsspring.wildfly.backoffice.api;

import jeevsspring.wildfly.backoffice.api.json.*;
import jeevsspring.wildfly.backoffice.service.PlayerService;
import jeevsspring.wildfly.backoffice.service.ServiceException;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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
    public Status test() {
        logger.trace("test()");
        Status out = new Status();
        out.setMessage("Test completed");
        return out;
    }

    @POST
    @Path("/wallet")
    public WalletOut wallet(WalletIn in) {
        logger.trace("wallet(" + in + ")");
        WalletOut out = new WalletOut();
        try {
            out = playerService.wallet(in.getPlayerId(), in.getSessionId(), in.getSessionToken());
        } catch (ServiceException e) {
            logger.error(e);
            out.setError(true);
            out.setErrorCode(e.getErrorCode());
        }
        logger.debug("wallet(" + in + ") return " + out);
        return out;
    }

    @POST
    @Path("/account")
    public AccountOut account(AccountIn in) {
        logger.trace("account(" + in + ")");
        AccountOut out = new AccountOut();
        try {
            out = playerService.account(in.getPlayerId(), in.getSessionId(), in.getSessionToken());
        } catch (ServiceException e) {
            logger.error(e);
            out.setError(true);
            out.setErrorCode(e.getErrorCode());
        }
        logger.debug("account(" + in + ") return " + out);
        return out;
    }

    @POST
    @Path("/login")
    public LoginOut login(LoginIn in) {
        logger.trace("login(" + in + ")");
        LoginOut out = new LoginOut();
        try {
            out = playerService.login(in.getUsername(), in.getPassword());
        } catch (ServiceException e) {
            logger.error(e);
            out.setError(true);
            out.setErrorCode(e.getErrorCode());
        }
        logger.debug("login(" + in + ") return " + out);
        return out;
    }

    @POST
    @Path("/logout")
    public LogoutOut logout(LogoutIn in) {
        logger.trace("logout(" + in + ")");
        LogoutOut out = new LogoutOut();
        try {
            out = playerService.logout(in.getPlayerId(), in.getSessionId(), in.getSessionToken());
        } catch (ServiceException e) {
            logger.error(e);
            out.setError(true);
            out.setErrorCode(e.getErrorCode());
        }
        logger.debug("logout(" + in + ") return " + out);
        return out;
    }

    @POST
    @Path("/stake")
    public StakeOut stake(StakeIn in) {
        logger.trace("stake(" + in + ")");
        StakeOut out = new StakeOut();
        try {
            out = playerService.stake(in.getPlayerId(), in.getSessionId(), in.getSessionToken(), in.getAmount());
        } catch (ServiceException e) {
            logger.error(e);
            out.setError(true);
            out.setErrorCode(e.getErrorCode());
        }
        logger.debug("stake(" + in + ") return " + out);
        return out;
    }

    @POST
    @Path("/win")
    public WinOut win(WinIn in) {
        logger.trace("win(" + in + ")");
        WinOut out = new WinOut();
        try {
            out = playerService.win(in.getPlayerId(), in.getSessionId(), in.getSessionToken(), in.getAmount());
        } catch (ServiceException e) {
            logger.error(e);
            out.setError(true);
            out.setErrorCode(e.getErrorCode());
        }
        logger.debug("win(" + in + ") return " + out);
        return out;
    }

    @POST
    @Path("/refund")
    public RefundOut refund(RefundIn in) {
        logger.trace("refund(" + in + ")");
        RefundOut out = new RefundOut();
        try {
            out = playerService.refund(in.getPlayerId(), in.getSessionId(), in.getSessionToken(), in.getAmount());
        } catch (ServiceException e) {
            logger.error(e);
            out.setError(true);
            out.setErrorCode(e.getErrorCode());
        }
        logger.debug("refund(" + in + ") return " + out);
        return out;
    }
}
