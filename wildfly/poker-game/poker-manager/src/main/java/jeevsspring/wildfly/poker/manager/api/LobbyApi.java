package jeevsspring.wildfly.poker.manager.api;

import jeevsspring.wildfly.poker.manager.api.json.lobby.*;
import jeevsspring.wildfly.poker.manager.bo.BoClient;
import jeevsspring.wildfly.poker.manager.bo.json.SigninIn;
import jeevsspring.wildfly.poker.manager.bo.json.SigninOut;
import jeevsspring.wildfly.poker.manager.bo.json.SignoutIn;
import jeevsspring.wildfly.poker.manager.bo.json.SignoutOut;
import jeevsspring.wildfly.poker.manager.engine.game.Game;
import jeevsspring.wildfly.poker.manager.engine.game.GameAction;
import jeevsspring.wildfly.poker.manager.engine.game.Games;
import jeevsspring.wildfly.poker.manager.engine.table.TableAction;
import jeevsspring.wildfly.poker.manager.engine.table.TableActionType;
import jeevsspring.wildfly.poker.manager.lobby.LobbyPlayers;

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

    @EJB
    private BoClient boClient;

    @EJB
    private LobbyPlayers lobbyPlayers;

    @EJB
    private Games games;

    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        return "Test completed";
    }

    @POST
    @Path("/login")
    public LoginOut login(LoginIn in) {
        LoginOut out = new LoginOut();
        SigninIn signinIn = new SigninIn();
        signinIn.setUsername(in.getUsername());
        signinIn.setPassword(in.getPassword());
        SigninOut signinOut = boClient.signin(signinIn);
        String playerId = signinOut.getUser().getId();
        lobbyPlayers.login(signinOut.getSessionId(), playerId);
        return out;
    }

    @POST
    @Path("/logout")
    public LogoutOut logout(LogoutIn in) {
        LogoutOut out = new LogoutOut();
        SignoutIn signoutIn = new SignoutIn();
        signoutIn.setSessionId(in.getSessionId());
        signoutIn.setToken(in.getToken());
        SignoutOut signoutOut = boClient.signout(signoutIn);
        lobbyPlayers.logout(signoutIn.getSessionId());
        return out;
    }

    @POST
    @Path("/show-tables")
    public ShowOut showTables(ShowIn in) {
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
        return out;
    }

    @POST
    @Path("/enter-table")
    public EnterOut enterTable(EnterIn in) {
        EnterOut out = new EnterOut();
        String playerId = lobbyPlayers.getPlayerId(in.getSessionId());
        Game game = games.get(in.getTableId());
        GameAction action = game.action(new TableAction(TableActionType.ENTER, in.getTableId(), playerId, null));
        out.setSessionId(in.getSessionId());
        out.setToken(in.getToken());
        return out;
    }

    @POST
    @Path("/quit-table")
    public QuitOut quitTable(QuitIn in) {
        QuitOut out = new QuitOut();
        String playerId = lobbyPlayers.getPlayerId(in.getSessionId());
        Game game = games.get(in.getTableId());
        game.action(new TableAction(TableActionType.QUIT, in.getTableId(), playerId, null));
        out.setSessionId(in.getSessionId());
        out.setToken(in.getToken());
        return out;
    }

}
