package jeevsspring.wildfly.poker.manager.api;

import jeevsspring.wildfly.poker.manager.api.json.lobby.*;
import jeevsspring.wildfly.poker.manager.bo.BoClient;
import jeevsspring.wildfly.poker.manager.bo.json.SigninIn;
import jeevsspring.wildfly.poker.manager.bo.json.SignoutIn;

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

    private BoClient boClient;

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
        SigninIn signin = new SigninIn();
        signin.setUsername(in.getUsername());
        signin.setPassword(in.getPassword());
        boClient.signin(signin);
        return out;
    }

    @POST
    @Path("/logout")
    public LogoutOut logout(LogoutIn in) {
        LogoutOut out = new LogoutOut();
        SignoutIn signout = new SignoutIn();
        signout.setSession(in.getSession());
        signout.setToken(in.getToken());
        boClient.signout(signout);
        return out;
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
