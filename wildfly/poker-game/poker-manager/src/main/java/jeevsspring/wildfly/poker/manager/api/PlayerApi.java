package jeevsspring.wildfly.poker.manager.api;

import jeevsspring.wildfly.poker.manager.api.json.Status;
import jeevsspring.wildfly.poker.manager.api.json.hand.PlayerOut;
import jeevsspring.wildfly.poker.manager.api.json.player.*;
import jeevsspring.wildfly.poker.manager.bo.BOException;
import jeevsspring.wildfly.poker.manager.bo.json.*;
import jeevsspring.wildfly.poker.manager.game.player.PlayerManager;
import org.jboss.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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
            BOWalletOut bo = playerManager.getWallet(in.getSessionId(), in.getToken());
            out.setBalance(bo.getBalance());
        } catch (BOException e) {
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
            BOAccountOut bo = playerManager.getAccount(in.getSessionId(), in.getToken());
            out.setFirstName(bo.getFirstName());
            out.setLastName(bo.getLastName());
        } catch (BOException e) {
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
            BOLoginOut bo = playerManager.login(in.getUsername(), in.getPassword());
            out.setSessionId(bo.getSessionId());
            out.setToken(bo.getToken());
            out.setNickname(bo.getPlayer().getNickname());
            out.setLife(bo.getLife());
            out.setTime(bo.getTime());

            // Set Account
            AccountOut account = new AccountOut();
            account.setFirstName(bo.getAccount().getFirstName());
            account.setLastName(bo.getAccount().getLastName());
            out.setAccount(account);

            // Set Wallet
            WalletOut wallet = new WalletOut();
            wallet.setBalance(bo.getWallet().getBalance());
            out.setWallet(wallet);
        } catch (BOException e) {
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
            BOLogoutOut bo = playerManager.logout(in.getSessionId(), in.getToken());
        } catch (BOException e) {
            logger.error(e);
            out.setError(true);
            out.setErrorCode("PLAYER_ERROR");
        }
        logger.debug("logout(" + in + ") return " + out);
        return out;
    }
}
