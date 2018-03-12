package jeevsspring.wildfly.poker.manager.api;

import jeevsspring.wildfly.poker.manager.api.json.Status;
import jeevsspring.wildfly.poker.manager.api.json.lobby.*;
import jeevsspring.wildfly.poker.manager.game.engine.Game;
import jeevsspring.wildfly.poker.manager.game.Games;
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
        out.setSessionToken(in.getToken());
        logger.debug("show(" + in + ") return " + out);
        return out;
    }

}
