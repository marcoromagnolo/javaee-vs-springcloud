package jeevsspring.wildfly.poker.manager.api;

import jeevsspring.wildfly.poker.common.TableSettings;
import jeevsspring.wildfly.poker.manager.api.json.Status;
import jeevsspring.wildfly.poker.manager.api.json.lobby.*;
import jeevsspring.wildfly.poker.manager.game.GameException;
import jeevsspring.wildfly.poker.manager.game.engine.Game;
import jeevsspring.wildfly.poker.manager.game.Games;
import org.jboss.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Stateless
@LocalBean
@Path("/lobby")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LobbyApi {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @EJB
    private Games<? extends Game> games;

    @GET
    @Path("/test")
    public Status test() {
        logger.trace("test()");
        Status out = new Status();
        out.setMessage("Test completed");
        return out;
    }

    @POST
    @Path("/tables")
    public Response tables(TablesIn in) {
        logger.trace("tables(" + in + ")");

        Response response;
        TablesOut out = new TablesOut();
        out.setTables(new ArrayList<>());
        List<? extends Game> tables = games.getAll();
        for (Game table : tables) {
            Table lobbyTable = new Table();
            lobbyTable.setId(table.getTableId());
            lobbyTable.setName(table.getSettings().getName());
            out.getTables().add(lobbyTable);
        }
        out.setSessionId(in.getSessionId());
        out.setSessionToken(in.getSessionToken());
        response = Response.ok(out).build();

        logger.debug("tables(" + in + ") return " + out);
        return response;
    }

    @POST
    @Path("/table-settings")
    public Response tableSettings(TableSettingsIn in) {
        logger.trace("tableSettings(" + in + ")");

        Response response;
        TableSettingsOut out = new TableSettingsOut();
        try {
            TableSettings settings = games.get(in.getTableId()).getSettings();
            out.setId(in.getTableId());
            out.setName(settings.getName());
            out.setGameType(settings.getGameType().name());
            out.setActionTimeout(settings.getActionTimeout());
            out.setNumberOfSeats(settings.getNumberOfSeats());
            out.setStartTimeout(settings.getStartTimeout());
            out.setSessionId(in.getSessionId());
            out.setSessionToken(in.getSessionToken());
            response = Response.ok(out).build();
        } catch (GameException e) {
            logger.error(e);
            out.setError(e.getError());
            response = Response.serverError()
                    .entity(out)
                    .status(Response.Status.BAD_REQUEST).build();
        }

        logger.debug("tableSettings(" + in + ") return " + out);
        return response;
    }

}
