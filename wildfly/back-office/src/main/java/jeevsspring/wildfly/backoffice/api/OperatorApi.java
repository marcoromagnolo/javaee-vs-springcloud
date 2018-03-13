package jeevsspring.wildfly.backoffice.api;

import jeevsspring.wildfly.backoffice.api.json.*;
import jeevsspring.wildfly.backoffice.service.OperatorService;
import jeevsspring.wildfly.backoffice.service.ServiceException;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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
    public Status test() {
        logger.trace("test()");
        Status out = new Status();
        out.setMessage("Test completed");
        return out;
    }

    @POST
    @Path("/login")
    public OperatorLoginOut login(OperatorLoginIn in) {
        logger.trace("login(" + in + ")");
        OperatorLoginOut out = new OperatorLoginOut();
        try {
            out = operatorService.login(in.getUsername(), in.getPassword());
        } catch (ServiceException e) {
            logger.error(e);
            out.setError(true);
            out.setErrorCode(e.getErrorCode());
        }
        logger.debug("login(" + in + ") return " + out);
        return out;
    }

    @POST
    @Path("/logout")
    public OperatorLogoutOut logout(OperatorLogoutIn in) {
        logger.trace("logout(" + in + ")");
        OperatorLogoutOut out = new OperatorLogoutOut();
        try {
            out = operatorService.logout(in.getOperatorId(), in.getSessionId(), in.getSessionToken());
        } catch (ServiceException e) {
            logger.error(e);
            out.setError(true);
            out.setErrorCode(e.getErrorCode());
        }
        logger.debug("logout(" + in + ") return " + out);
        return out;
    }
}
