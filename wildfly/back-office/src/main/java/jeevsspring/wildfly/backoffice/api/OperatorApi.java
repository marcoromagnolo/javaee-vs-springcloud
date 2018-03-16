package jeevsspring.wildfly.backoffice.api;

import jeevsspring.wildfly.backoffice.api.json.*;
import jeevsspring.wildfly.backoffice.service.AuthenticationException;
import jeevsspring.wildfly.backoffice.service.OperatorService;
import jeevsspring.wildfly.backoffice.service.BackOfficeException;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("/operator")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OperatorApi {

    // JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @Inject
    private OperatorService operatorService;

    @GET
    @Path("/test")
    public Response test() {
        logger.trace("test()");
        return Response.ok("Test completed").build();
    }

    @POST
    @Path("/login")
    public Response login(OperatorLoginIn in) {
        logger.trace("login(" + in + ")");

        Response response;
        try {
            OperatorLoginOut out = operatorService.login(in.getUsername(), in.getPassword());
            response = Response.ok(out, MediaType.APPLICATION_JSON).build();
        } catch (BackOfficeException e) {
            logger.error(e);
            response = Response.serverError().status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (AuthenticationException e) {
            logger.warn(e);
            response = Response.serverError()
                    .entity(new Status(e.getErrorCode()))
                    .status(Response.Status.FORBIDDEN).build();
        }
        logger.debug("login(" + in + ") return " + response);
        return response;
    }

    @POST
    @Path("/logout")
    public Response logout(OperatorLogoutIn in) {
        logger.trace("logout(" + in + ")");

        Response response;
        try {
            OperatorLogoutOut out = operatorService.logout(in.getOperatorId(), in.getSessionId(), in.getSessionToken());
            response = Response.ok(out, MediaType.APPLICATION_JSON).build();
        } catch (BackOfficeException e) {
            logger.error(e);
            response = Response.serverError()
                    .entity(new Status(e.getErrorCode()))
                    .status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        logger.debug("logout(" + in + ") return " + response);
        return response;
    }
}
