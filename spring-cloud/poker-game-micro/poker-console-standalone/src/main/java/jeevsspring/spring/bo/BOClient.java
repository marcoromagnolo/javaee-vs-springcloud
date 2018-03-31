package jeevsspring.spring.bo;

import jeevsspring.spring.bo.json.OperatorLoginIn;
import jeevsspring.spring.bo.json.OperatorLoginOut;
import jeevsspring.spring.bo.json.OperatorLogoutIn;
import jeevsspring.spring.bo.json.OperatorLogoutOut;
import jeevsspring.spring.util.PCConfig;
import org.springframework.beans.factory.annotation.Autowired;
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

    // Target Endpoint
    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private PCConfig config;
    private String target;

    @PostConstruct
    public void init() {
        target = config.getBoTargetUrl();
    }

    public OperatorLoginOut login(OperatorLoginIn in) throws BOException {
        logger.log(Level.FINEST, "login(" + in + ")");

        ResponseEntity rs = restTemplate.postForEntity(target + "operator/login", in, OperatorLoginOut.class);
        if (rs.getStatusCode() != HttpStatus.OK) {
            throw new BOException();
        }

        logger.log(Level.FINE, "login(" + in + ") return " + rs.getBody());
        return (OperatorLoginOut) rs.getBody();
    }

    public OperatorLogoutOut logout(OperatorLogoutIn in) throws BOException {
        logger.log(Level.FINEST, "logout(" + in + ")");

        ResponseEntity rs = restTemplate.postForEntity(target + "operator/login", in, OperatorLogoutOut.class);
        if (rs.getStatusCode() != HttpStatus.OK) {
            throw new BOException();
        }

        logger.log(Level.FINE, "logout(" + in + ") return " + rs.getBody());
        return (OperatorLogoutOut) rs.getBody();
    }
}
