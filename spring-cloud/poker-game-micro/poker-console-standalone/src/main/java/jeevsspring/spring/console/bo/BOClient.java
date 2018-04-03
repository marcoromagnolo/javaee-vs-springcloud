package jeevsspring.spring.console.bo;

import jeevsspring.spring.console.bo.json.OperatorLoginIn;
import jeevsspring.spring.console.bo.json.OperatorLoginOut;
import jeevsspring.spring.console.bo.json.OperatorLogoutIn;
import jeevsspring.spring.console.bo.json.OperatorLogoutOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private Environment config;

    private final String target = "http://poker-operator-bo/back-office/api";

    public OperatorLoginOut login(OperatorLoginIn in) throws BOException {
        logger.log(Level.FINEST, "login(" + in + ")");

        try {
            ResponseEntity rs = restTemplate.postForEntity(target + "/operator/login", in, OperatorLoginOut.class);
            if (rs.getStatusCode() != HttpStatus.OK) {
                throw new BOException();
            }
            logger.log(Level.FINE, "login(" + in + ") return " + rs.getBody());
            return (OperatorLoginOut) rs.getBody();
        } catch (RestClientException e) {
            throw new BOException();
        }
    }

    public OperatorLogoutOut logout(OperatorLogoutIn in) throws BOException {
        logger.log(Level.FINEST, "logout(" + in + ")");

        try {
            ResponseEntity rs = restTemplate.postForEntity(target + "operator/login", in, OperatorLogoutOut.class);
            if (rs.getStatusCode() != HttpStatus.OK) {
                throw new BOException();
            }
            logger.log(Level.FINE, "logout(" + in + ") return " + rs.getBody());
            return (OperatorLogoutOut) rs.getBody();
        } catch (RestClientException e) {
            throw new BOException();
        }
    }
}
