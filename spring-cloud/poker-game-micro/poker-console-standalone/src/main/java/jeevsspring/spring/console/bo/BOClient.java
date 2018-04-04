package jeevsspring.spring.console.bo;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import jeevsspring.spring.console.bo.json.OperatorLoginIn;
import jeevsspring.spring.console.bo.json.OperatorLoginOut;
import jeevsspring.spring.console.bo.json.OperatorLogoutIn;
import jeevsspring.spring.console.bo.json.OperatorLogoutOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

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

    private String target = "http://poker-operator-bo/back-office/api";

    public OperatorLoginOut login(OperatorLoginIn in) throws BOException {
        logger.log(Level.INFO, "login(" + in.getUsername() + ", " + in.getPassword() + ")");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<OperatorLoginIn> entity = new HttpEntity<>(in, headers);
        try {
            ResponseEntity rs = restTemplate.postForEntity(target + "/operator/login", entity, OperatorLoginOut.class);
            if (rs.getStatusCode() != HttpStatus.OK) {
                throw new BOException();
            }
            logger.log(Level.FINE, "login(" + in + ") return " + rs.getBody());
            return (OperatorLoginOut) rs.getBody();
        } catch (RestClientException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
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
