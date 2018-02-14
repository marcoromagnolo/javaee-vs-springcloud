package jeevsspring.wildfly.poker.manager.api;

import jeevsspring.wildfly.poker.manager.api.json.lobby.*;
import jeevsspring.wildfly.poker.manager.bo.BoClient;
import jeevsspring.wildfly.poker.manager.bo.json.SigninIn;
import jeevsspring.wildfly.poker.manager.bo.json.SigninOut;
import jeevsspring.wildfly.poker.manager.bo.json.SignoutIn;
import jeevsspring.wildfly.poker.manager.bo.json.SignoutOut;
import jeevsspring.wildfly.poker.manager.lobby.Lobby;

import javax.ejb.EJB;
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

    @EJB
    private BoClient boClient;

    @EJB
    private Lobby lobby;

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
        lobby.login(signinOut.getSessionId(), playerId);
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
        lobby.logout(signoutIn.getSessionId());
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
