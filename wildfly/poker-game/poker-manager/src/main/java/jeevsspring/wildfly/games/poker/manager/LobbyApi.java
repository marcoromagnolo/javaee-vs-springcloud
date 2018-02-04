package jeevsspring.wildfly.games.poker.manager;

import jeevsspring.wildfly.games.poker.manager.json.LoginIn;
import jeevsspring.wildfly.games.poker.manager.json.LoginOut;
import jeevsspring.wildfly.games.poker.manager.json.LogoutIn;
import jeevsspring.wildfly.games.poker.manager.json.LogoutOut;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Stateless
@LocalBean
@Path("/lobby")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LobbyApi {

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
        out.setMessage("Bye Bye!");
        return out;
    }

    @POST
    @Path("/logout")
    public LogoutOut logout(LogoutIn in) {
        return new LogoutOut();
    }


}
