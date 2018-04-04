package jeevsspring.spring.poker.manager.player.bo;

import jeevsspring.spring.poker.manager.player.bo.json.*;
import jeevsspring.spring.poker.manager.player.manager.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marco Romagnolo
 */
@Service
public class BOClient {

    private final Logger logger = Logger.getLogger(getClass().toString());

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment config;

    private String target = "http://poker-player-bo/back-office/api";

    public BOLoginOut login(BOLoginIn in) throws BOException {
        logger.log(Level.FINEST, "login(" + in + ")");

        ResponseEntity<BOLoginOut> rs;
        try {
            rs = restTemplate.postForEntity(target + "/player/login", in, BOLoginOut.class);
            BOLoginOut out = rs.getBody();
            logger.log(Level.INFO, "login(" + in + ") return " + out);
            return out;
        } catch (HttpClientErrorException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            throw new BOException("Invalid Credentials");
        }
    }

    public BOLogoutOut logout(BOLogoutIn in) throws BOException {
        logger.log(Level.FINEST, "logout(" + in + ")");

        try {
            ResponseEntity<BOLogoutOut> rs = restTemplate.postForEntity(target + "/player/logout", in, BOLogoutOut.class);
            logger.log(Level.FINE, "JSON REST response: " + rs.getBody());
            if (rs.getStatusCode() != HttpStatus.OK) {
                throw new BOException(rs.getBody().getError());
            }
            BOLogoutOut out = rs.getBody();
            out.setMessage("Bye Bye!");
            logger.log(Level.FINE, "logout(" + in + ") return " + out);
            return out;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new BOException(ErrorCode.INTERNAL_SERVER_ERROR.name());
        }
    }

    public BOSessionRefreshOut sessionRefresh(BOSessionRefreshIn in) throws BOException {
        logger.log(Level.FINEST, "sessionRefresh(" + in + ")");

        try {
            ResponseEntity<BOSessionRefreshOut> rs = restTemplate.postForEntity(target + "/player/session-refresh", in, BOSessionRefreshOut.class);
            logger.log(Level.FINE, "JSON REST response: " + rs.getBody());
            if (rs.getStatusCode() != HttpStatus.OK) {
                throw new BOException(rs.getBody().getError());
            }
            BOSessionRefreshOut out = rs.getBody();
            logger.log(Level.FINE, "sessionRefresh(" + in + ") return " + out);
            return out;
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new BOException(ErrorCode.INTERNAL_SERVER_ERROR.name());
        }
    }

    public BOWalletOut wallet(BOWalletIn boWalletIn) {
        // TODO
        return null;
    }

    public BOAccountOut account(BOAccountIn accountIn) {
        // TODO
        return null;
    }
}
