package jeevsspring.spring.backoffice.operator.api;

import jeevsspring.spring.backoffice.operator.api.json.*;
import jeevsspring.spring.backoffice.operator.service.AuthenticationException;
import jeevsspring.spring.backoffice.operator.service.InvalidSessionException;
import jeevsspring.spring.backoffice.operator.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/operator")
public class OperatorApi {

    private final Logger logger = Logger.getLogger(getClass().toString());

    @Autowired
    private OperatorService operatorService;

    @GetMapping("/test")
    public ResponseEntity test() {
        logger.log(Level.FINEST, "test()");
        return ResponseEntity.ok("Test completed");
    }

    @PostMapping("/login")
    public ResponseEntity login(OperatorLoginIn in) {
        logger.log(Level.FINEST,"login(" + in + ")");

        ResponseEntity response;
        try {
            OperatorLoginOut out = operatorService.login(in.getUsername(), in.getPassword());
            response = ResponseEntity.ok(out);
        } catch (AuthenticationException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Status(e.getErrorCode()));
        }

        logger.log(Level.FINE,"login(" + in + ") return " + response);
        return response;
    }

    @PostMapping("/logout")
    public ResponseEntity login(OperatorLogoutIn in) {
        logger.log(Level.FINEST,"logout(" + in + ")");

        ResponseEntity response;
        try {
            OperatorLogoutOut out = operatorService.logout(in.getOperatorId(), in.getSessionId(), in.getSessionToken());
            response = ResponseEntity.ok(out);
        } catch (InvalidSessionException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            response = ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Status(e.getErrorCode()));
        }

        logger.log(Level.FINE,"logout(" + in + ") return " + response);
        return response;
    }

}
