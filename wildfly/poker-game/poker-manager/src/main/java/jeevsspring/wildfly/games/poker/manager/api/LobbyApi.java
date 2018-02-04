package jeevsspring.wildfly.games.poker.manager.api;

import jeevsspring.wildfly.games.poker.manager.api.json.*;

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

    @POST
    @Path("/register")
    public RegisterOut register(RegisterIn in) {
        RegisterOut out = new RegisterOut();
        return out;
    }

    @POST
    @Path("/unregister")
    public UnregisterOut unregister(UnregisterIn in) {
        return new UnregisterOut();
    }

    @POST
    @Path("/enter")
    public EnterOut enter(EnterIn in) {
        EnterOut out = new EnterOut();
        return out;
    }

    @POST
    @Path("/quit")
    public QuitOut quit(QuitIn in) {
        return new QuitOut();
    }

    @POST
    @Path("/show")
    public ShowOut show(ShowIn in) {
        return new ShowOut();
    }

}
