package jeevsspring.wildfly.backoffice.api;

import jeevsspring.wildfly.backoffice.api.json.*;
import jeevsspring.wildfly.backoffice.service.ServiceException;
import jeevsspring.wildfly.backoffice.service.PlayerService;
import jeevsspring.wildfly.poker.manager.api.json.Status;
import jeevsspring.wildfly.poker.manager.bo.PlayerException;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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
            playerService.wallet();
            out.setBalance(bo.getBalance());
        } catch (ServiceException e) {
            logger.error(e);
            out.setError(true);
            out.setErrorCode("PLAYER_ERROR");
        }
        logger.debug("wallet(" + in + ") return " + out);
        return out;
    }

    @POST
    @Path("/account")
    public AccountOut account(AccountIn in) {
        logger.trace("wallet(" + in + ")");
        AccountOut out = new AccountOut();
        try {
            playerService.account();
            out.setFirstName(bo.getFirstName());
            out.setLastName(bo.getLastName());
        } catch (ServiceException e) {
            logger.error(e);
            out.setError(true);
            out.setErrorCode("PLAYER_ERROR");
        }
        logger.debug("login(" + in + ") return " + out);
        return out;
    }

    @POST
    @Path("/login")
    public LoginOut login(LoginIn in) {
        logger.trace("login(" + in + ")");
        LoginOut out = new LoginOut();
        try {
            playerService.login();
            
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
        } catch (ServiceException e) {
            logger.error(e);
            out.setError(true);
            out.setErrorCode("PLAYER_ERROR");
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
            playerService.logout();
        } catch (ServiceException e) {
            logger.error(e);
            out.setError(true);
            out.setErrorCode("PLAYER_ERROR");
        }
        logger.debug("logout(" + in + ") return " + out);
        return out;
    }

    @POST
    @Path("/stake")
    public WalletOut stake(WalletIn in) {
        logger.trace("stake(" + in + ")");
        WalletOut out = new WalletOut();
        try {
            out.setBalance(bo.getBalance());
        } catch (ServiceException e) {
            logger.error(e);
            out.setError(true);
            out.setErrorCode("PLAYER_ERROR");
        }
        logger.debug("stake(" + in + ") return " + out);
        return out;
    }

    @POST
    @Path("/win")
    public WalletOut win(WalletIn in) {
        logger.trace("win(" + in + ")");
        WalletOut out = new WalletOut();
        try {
            out.setBalance(bo.getBalance());
        } catch (ServiceException e) {
            logger.error(e);
            out.setError(true);
            out.setErrorCode("PLAYER_ERROR");
        }
        logger.debug("win(" + in + ") return " + out);
        return out;
    }

    @POST
    @Path("/refund")
    public WalletOut refund(WalletIn in) {
        logger.trace("refund(" + in + ")");
        WalletOut out = new WalletOut();
        try {
            out.setBalance(bo.getBalance());
        } catch (ServiceException e) {
            logger.error(e);
            out.setError(true);
            out.setErrorCode("PLAYER_ERROR");
        }
        logger.debug("refund(" + in + ") return " + out);
        return out;
    }
}
