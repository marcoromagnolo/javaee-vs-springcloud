package jeevsspring.spring.backoffice.operator.api;

import jeevsspring.spring.backoffice.operator.api.json.Status;
import jeevsspring.spring.backoffice.operator.service.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class ErrorHandler {

    private final Logger logger = Logger.getLogger(getClass().toString());

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Status> handleThrowable(final Throwable ex) {
        logger.log(Level.SEVERE, ex.getMessage(), ex);
        return new ResponseEntity<>(new Status(ErrorCode.BACKOFFICE_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
