package jeevsspring.spring.poker.manager.player.bo;

import jeevsspring.spring.poker.manager.player.bo.json.*;
import jeevsspring.spring.poker.manager.player.manager.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

    private RestTemplate restTemplate = new RestTemplate();

    private String target;

    @Autowired
    private Environment config;

    @PostConstruct
    public void init() {
        target = config.getProperty("bo.target.url");
    }

    public BOLoginOut login(BOLoginIn in) throws BOException {
        logger.log(Level.FINEST, "login(" + in + ")");

        try {
            ResponseEntity<BOLoginOut> rs = restTemplate.postForEntity(target + "/player/login", in, BOLoginOut.class);
            logger.log(Level.FINE, "JSON REST response: " + rs.getBody());
            if (rs.getStatusCode() != HttpStatus.OK) {
                throw new BOException(rs.getBody().getError());
            }
            BOLoginOut out = rs.getBody();
            logger.log(Level.FINE, "login(" + in + ") return " + out);
            return out;
        } catch (Exception e) {
            throw new BOException(ErrorCode.INTERNAL_SERVER_ERROR.name());
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
