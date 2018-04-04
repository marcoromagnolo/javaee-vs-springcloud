package jeevsspring.spring.poker.manager.player.api;

import jeevsspring.spring.poker.manager.player.api.json.Status;
import jeevsspring.spring.poker.manager.player.manager.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Logger;

@ControllerAdvice
public class ErrorHandler {

    private final Logger logger = Logger.getLogger(getClass().toString());

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Status> handleThrowable(final Throwable ex) {
        return new ResponseEntity<Status>(new Status(ErrorCode.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
