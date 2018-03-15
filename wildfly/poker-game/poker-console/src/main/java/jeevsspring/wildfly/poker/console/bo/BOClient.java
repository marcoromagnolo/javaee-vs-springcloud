package jeevsspring.wildfly.poker.console.bo;

import jeevsspring.wildfly.poker.console.bo.json.OperatorLoginIn;
import jeevsspring.wildfly.poker.console.bo.json.OperatorLoginOut;
import jeevsspring.wildfly.poker.console.bo.json.OperatorLogoutIn;
import jeevsspring.wildfly.poker.console.bo.json.OperatorLogoutOut;
import jeevsspring.wildfly.poker.console.util.PCConfig;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Marco Romagnolo
 */
@ApplicationScoped
public class BOClient {

    // Jboss Logger
    private final Logger logger = Logger.getLogger(getClass());

    // Target Endpoint
    private WebTarget target;

    @Inject
    private PCConfig config;

    @PostConstruct
    public void init() {
        Client client = ClientBuilder.newClient();
        target = client.target(config.getBoTargetUrl());
    }

    public OperatorLoginOut login(OperatorLoginIn in) throws BOException {
        logger.trace("login(" + in + ")");

        Response rs = target
                .path("player/login")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(in), Response.class);
        OperatorLoginOut out = rs.readEntity(OperatorLoginOut.class);
        if (rs.getStatus() != 200) {
            throw new BOException();
        }

        logger.debug("login(" + in + ") return " + out);
        return out;
    }

    public OperatorLogoutOut logout(OperatorLogoutIn in) throws BOException {
        logger.trace("logout(" + in + ")");

        Response rs = target
                .path("player/logout")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(in), Response.class);
        OperatorLogoutOut out = rs.readEntity(OperatorLogoutOut.class);
        out.setMessage("Bye Bye!");
        if (rs.getStatus() != 200) {
            throw new BOException();
        }

        logger.debug("logout(" + in + ") return " + out);
        return out;
    }
}
