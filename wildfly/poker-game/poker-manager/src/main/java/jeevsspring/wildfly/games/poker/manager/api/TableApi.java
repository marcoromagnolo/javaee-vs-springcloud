package jeevsspring.wildfly.games.poker.manager.api;

import jeevsspring.wildfly.games.poker.manager.api.json.*;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Stateless
@LocalBean
@Path("/table")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TableApi {

    @GET
    @Path("/updates")
    public Updates updates(PlayerSession in) {
        Updates out = new Updates();
        return out;
    }

    @POST
    @Path("/buyin")
    public BuyinOut buyin(BuyinIn in) {
        BuyinOut out = new BuyinOut();
        return out;
    }

    @POST
    @Path("/buyout")
    public BuyoutOut buyout(BuyoutIn in) {
        BuyoutOut out = new BuyoutOut();
        return out;
    }

}
