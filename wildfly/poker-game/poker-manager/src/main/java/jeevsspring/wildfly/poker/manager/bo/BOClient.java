package jeevsspring.wildfly.poker.manager.bo;

import jeevsspring.wildfly.poker.manager.bo.json.*;
import jeevsspring.wildfly.poker.manager.util.PMConfig;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Marco Romagnolo
 */
@Stateless
@LocalBean
public class BOClient {

    // Jboss Logger
    private final Logger logger = Logger.getLogger(getClass());

    // Target Endpoint
    private WebTarget target;

    @EJB
    private PMConfig config;

    @PostConstruct
    public void init() {
        Client client = ClientBuilder.newClient();
        target = client.target(config.getBoTargetUrl());
    }

    public BOLoginOut login(BOLoginIn in) throws BOException {
        logger.trace("login(" + in + ")");

        Response rs = target
                .path("player/login")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(in), Response.class);
        BOLoginOut out = rs.readEntity(new GenericType<BOLoginOut>(){});
        if (rs.getStatus() != 200) {
            throw new BOException();
        }

        logger.debug("login(" + in + ") return " + out);
        return out;
    }

    public BOLogoutOut logout(BOLogoutIn in) throws BOException {
        logger.trace("logout(" + in + ")");

        Response rs = target
                .path("player/logout")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(in), Response.class);
        BOLogoutOut out = rs.readEntity(BOLogoutOut.class);
        out.setMessage("Bye Bye!");
        if (rs.getStatus() != 200) {
            throw new BOException();
        }

        logger.debug("logout(" + in + ") return " + out);
        return out;
    }

    public BOSessionRefreshOut sessionRefresh(BOSessionRefreshIn in) throws BOException {
        logger.trace("sessionRefresh(" + in + ")");

        Response rs = target
                .path("player/session-refresh")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(in), Response.class);
        BOSessionRefreshOut out = rs.readEntity(BOSessionRefreshOut.class);
        if (rs.getStatus() != 200) {
            throw new BOException();
        }

        logger.debug("sessionRefresh(" + in + ") return " + out);
        return out;
    }

    public BOWinOut win(BOWinIn in) throws BOException {
        logger.trace("win(" + in + ")");

        Response rs = target
                .path("player/win")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(in), Response.class);
        BOWinOut out = rs.readEntity(BOWinOut.class);
        if (rs.getStatus() != 200) {
            throw new BOException();
        }

        logger.debug("win(" + in + ") return " + out);
        return out;
    }

    public BOStakeOut stake(BOStakeIn in) throws BOException {
        logger.trace("stake(" + in + ")");

        Response rs = target
                .path("player/stake")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(in), Response.class);
        BOStakeOut out = rs.readEntity(BOStakeOut.class);
        if (rs.getStatus() != 200) {
            throw new BOException();
        }

        logger.debug("stake(" + in + ") return " + out);
        return out;

    }

    public BORefundOut refund(BORefundIn in) throws BOException {
        logger.trace("refund(" + in + ")");

        Response rs = target
                .path("player/refund")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(in), Response.class);
        BORefundOut out = rs.readEntity(BORefundOut.class);
        if (rs.getStatus() != 200) {
            throw new BOException();
        }

        logger.debug("refund(" + in + ") return " + out);
        return out;
    }

    public BOWalletOut wallet(BOWalletIn in) throws BOException {
        logger.trace("refund(" + in + ")");

        Response rs = target
                .path("player/wallet")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(in), Response.class);
        BOWalletOut out = rs.readEntity(BOWalletOut.class);
        if (rs.getStatus() != 200) {
            throw new BOException();
        }

        logger.debug("refund(" + in + ") return " + out);
        return out;
    }

    public BOAccountOut account(BOAccountIn in) throws BOException {
        logger.trace("account(" + in + ")");

        Response rs = target
                .path("player/account")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(in), Response.class);
        BOAccountOut out = rs.readEntity(BOAccountOut.class);
        if (rs.getStatus() != 200) {
            throw new BOException();
        }

        logger.debug("account(" + in + ") return " + out);
        return out;
    }
}
