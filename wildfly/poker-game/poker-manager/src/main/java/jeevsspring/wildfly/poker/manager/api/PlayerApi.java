package jeevsspring.wildfly.poker.manager.api;

import jeevsspring.wildfly.poker.manager.api.json.Status;
import jeevsspring.wildfly.poker.manager.api.json.player.*;
import jeevsspring.wildfly.poker.manager.bo.BOException;
import jeevsspring.wildfly.poker.manager.bo.BoClient;
import jeevsspring.wildfly.poker.manager.bo.json.*;
import jeevsspring.wildfly.poker.manager.lobby.LobbyPlayers;
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
    private BoClient boClient;

    @EJB
    private LobbyPlayers lobbyPlayers;

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
            BOWalletIn walletIn = new BOWalletIn();
            walletIn.setSessionId(in.getSessionId());
            walletIn.setToken(in.getToken());
            BOWalletOut boWalletOut = boClient.wallet(walletIn);
            out.setBalance(boWalletOut.getBalance());
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
            BOAccountIn accountIn = new BOAccountIn();
            accountIn.setSessionId(in.getSessionId());
            accountIn.setToken(in.getToken());
            BOAccountOut accountOut = boClient.account(accountIn);
//            out.set
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
            BOLoginIn signinIn = new BOLoginIn();
            signinIn.setUsername(in.getUsername());
            signinIn.setPassword(in.getPassword());
            BOLoginOut signinOut = boClient.login(signinIn);
            String playerId = signinOut.getUser().getId();
            lobbyPlayers.login(signinOut.getSessionId(), playerId);
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
            BOLogoutIn boLogoutIn = new BOLogoutIn();
            boLogoutIn.setSessionId(in.getSessionId());
            boLogoutIn.setToken(in.getToken());
            BOLogoutOut boLogoutOut = boClient.logout(boLogoutIn);
            lobbyPlayers.logout(boLogoutIn.getSessionId());
        } catch (BOException e) {
            logger.error(e);
            out.setError(true);
            out.setErrorCode("PLAYER_ERROR");
        }
        logger.debug("logout(" + in + ") return " + out);
        return out;
    }
}
