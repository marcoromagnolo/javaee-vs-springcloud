package jeevsspring.wildfly.poker.manager.api;

import jeevsspring.wildfly.poker.manager.api.json.Status;
import jeevsspring.wildfly.poker.manager.api.json.lobby.*;
import jeevsspring.wildfly.poker.manager.bo.BoClient;
import jeevsspring.wildfly.poker.manager.bo.json.SigninIn;
import jeevsspring.wildfly.poker.manager.bo.json.SigninOut;
import jeevsspring.wildfly.poker.manager.bo.json.SignoutIn;
import jeevsspring.wildfly.poker.manager.bo.json.SignoutOut;
import jeevsspring.wildfly.poker.manager.game.engine.Game;
import jeevsspring.wildfly.poker.manager.game.Games;
import jeevsspring.wildfly.poker.manager.lobby.LobbyPlayers;
import org.jboss.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Stateless
@LocalBean
@Path("/lobby")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LobbyApi {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @EJB
    private BoClient boClient;

    @EJB
    private LobbyPlayers lobbyPlayers;

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
    @Path("/login")
    public LoginOut login(LoginIn in) {
        logger.trace("login(" + in + ")");
        LoginOut out = new LoginOut();
        SigninIn signinIn = new SigninIn();
        signinIn.setUsername(in.getUsername());
        signinIn.setPassword(in.getPassword());
        SigninOut signinOut = boClient.signin(signinIn);
        String playerId = signinOut.getUser().getId();
        lobbyPlayers.login(signinOut.getSessionId(), playerId);
        logger.debug("login(" + in + ") return " + out);
        return out;
    }

    @POST
    @Path("/logout")
    public LogoutOut logout(LogoutIn in) {
        logger.trace("logout(" + in + ")");
        LogoutOut out = new LogoutOut();
        SignoutIn signoutIn = new SignoutIn();
        signoutIn.setSessionId(in.getSessionId());
        signoutIn.setToken(in.getToken());
        SignoutOut signoutOut = boClient.signout(signoutIn);
        lobbyPlayers.logout(signoutIn.getSessionId());
        logger.debug("logout(" + in + ") return " + out);
        return out;
    }

    @POST
    @Path("/show")
    public ShowOut show(ShowIn in) {
        logger.trace("show(" + in + ")");
        ShowOut out = new ShowOut();
        Collection<Game> tables = games.getAll();
        for (Game table : tables) {
            LobbyTable lobbyTable = new LobbyTable();
            lobbyTable.setId(table.getTableId());
            lobbyTable.setName(table.getSettings().getName());
            out.getTables().add(lobbyTable);
        }
        out.setSessionId(in.getSessionId());
        out.setToken(in.getToken());
        logger.debug("show(" + in + ") return " + out);
        return out;
    }

}
