package jeevsspring.wildfly.poker.manager.api;

import jeevsspring.wildfly.poker.manager.api.json.Status;
import jeevsspring.wildfly.poker.manager.game.ErrorCode;
import org.jboss.logging.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ErrorHandler implements ExceptionMapper<Throwable> {

    //JBoss Logger
    private final Logger logger = Logger.getLogger(getClass());

    @Override
    public Response toResponse(Throwable throwable) {
        logger.error(throwable.getMessage(), throwable);
        return Response.serverError()
                .entity(new Status(ErrorCode.INTERNAL_SERVER_ERROR.name()))
                .status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
