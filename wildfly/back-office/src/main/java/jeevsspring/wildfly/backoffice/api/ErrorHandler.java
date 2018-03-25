package jeevsspring.wildfly.backoffice.api;

import jeevsspring.wildfly.backoffice.api.json.Status;
import jeevsspring.wildfly.backoffice.service.ErrorCode;
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
                .entity(new Status(ErrorCode.BACKOFFICE_ERROR))
                .status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
