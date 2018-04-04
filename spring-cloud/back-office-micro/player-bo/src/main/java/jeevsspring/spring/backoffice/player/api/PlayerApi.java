package jeevsspring.spring.backoffice.player.api;

import jeevsspring.spring.backoffice.player.api.json.*;
import jeevsspring.spring.backoffice.player.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/player")
public class PlayerApi {

    private final Logger logger = Logger.getLogger(getClass().toString());

    @Autowired
    private PlayerService playerService;

    @GetMapping("/test")
    public ResponseEntity test() {
        logger.log(Level.FINEST, "test()");
        return ResponseEntity.ok("Test completed");
    }

    @PostMapping("/wallet")
    public @ResponseBody ResponseEntity wallet(@RequestBody WalletIn in) {
        logger.log(Level.FINEST,"wallet(" + in + ")");

        ResponseEntity response;
        try {
            WalletOut out = playerService.wallet(in.getPlayerId(), in.getSessionId(), in.getSessionToken());
            response = ResponseEntity.ok(out);

        } catch (InvalidSessionException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Status(e.getErrorCode()));
        }

        logger.log(Level.FINE,"wallet(" + in + ") return " + response.getBody());
        return response;
    }

    @PostMapping("/account")
    public @ResponseBody ResponseEntity account(@RequestBody AccountIn in) {
        logger.log(Level.FINEST,"account(" + in + ")");

        ResponseEntity response;
        try {
            AccountOut out = playerService.account(in.getPlayerId(), in.getSessionId(), in.getSessionToken());
            response = ResponseEntity.ok(out);
        } catch (InvalidSessionException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            response = ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Status(e.getErrorCode()));
        }

        logger.log(Level.FINE,"account(" + in + ") return " + response.getBody());
        return response;
    }

    @PostMapping("/login")
    public @ResponseBody ResponseEntity login(@RequestBody LoginIn in) {
        logger.log(Level.FINEST,"login(" + in + ")");

        ResponseEntity response;
        try {
            LoginOut out = playerService.login(in.getUsername(), in.getPassword());
            response = ResponseEntity.ok(out);

        } catch (AuthenticationException e) {
            logger.log(Level.WARNING, e.getMessage());
            response = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Status(e.getErrorCode()));
        } catch (InconsistentDataException e) {
            logger.log(Level.WARNING, e.getMessage());
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Status(e.getErrorCode()));
        }

        logger.log(Level.FINE,"login(" + in + ") return " + response.getBody());
        return response;
    }

    @PostMapping("/logout")
    public @ResponseBody ResponseEntity logout(@RequestBody LogoutIn in) {
        logger.log(Level.FINEST,"logout(" + in + ")");

        ResponseEntity response;
        try {
            LogoutOut out = playerService.logout(in.getPlayerId(), in.getSessionId(), in.getSessionToken());
            response = ResponseEntity.ok(out);

        } catch (InvalidSessionException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            response = ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Status(e.getErrorCode()));
        }

        logger.log(Level.FINE,"logout(" + in + ") return " + response.getBody());
        return response;
    }

    @PostMapping("/session-refresh")
    public @ResponseBody ResponseEntity sessionRefresh(@RequestBody SessionRefreshIn in) {
        logger.log(Level.FINEST,"sessionRefresh(" + in + ")");

        ResponseEntity response;
        try {
            SessionRefreshOut out = playerService.sessionRefresh(in.getPlayerId(), in.getSessionId(), in.getSessionToken());
            response = ResponseEntity.ok(out);

        } catch (InvalidSessionException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            response = ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Status(e.getErrorCode()));
        }

        logger.log(Level.FINE,"sessionRefresh(" + in + ") return " + response.getBody());
        return response;
    }

    @PostMapping("/stake")
    public @ResponseBody ResponseEntity stake(@RequestBody StakeIn in) {
        logger.log(Level.FINEST,"stake(" + in + ")");

        ResponseEntity response;
        try {
            StakeOut out = playerService.stake(in.getPlayerId(), in.getSessionId(), in.getSessionToken(), in.getAmount());
            response = ResponseEntity.ok(out);

        } catch (InvalidSessionException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            response = ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Status(e.getErrorCode()));
        } catch (InvalidAmountException | InsufficientFundsException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Status(e.getErrorCode()));
        }

        logger.log(Level.FINE,"stake(" + in + ") return " + response.getBody());
        return response;
    }

    @PostMapping("/win")
    public @ResponseBody ResponseEntity win(@RequestBody WinIn in) {
        logger.log(Level.FINEST,"win(" + in + ")");

        ResponseEntity response;
        try {
            WinOut out = playerService.win(in.getPlayerId(), in.getSessionId(), in.getSessionToken(), in.getAmount());
            response = ResponseEntity.ok(out);

        } catch (InvalidSessionException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            response = ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Status(e.getErrorCode()));
        } catch (InvalidAmountException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Status(e.getErrorCode()));
        }

        logger.log(Level.FINE,"win(" + in + ") return " + response.getBody());
        return response;
    }

    @PostMapping("/refund")
    public @ResponseBody ResponseEntity refund(@RequestBody RefundIn in) {
        logger.log(Level.FINEST,"refund(" + in + ")");

        ResponseEntity response;
        try {
            RefundOut out = playerService.refund(in.getPlayerId(), in.getSessionId(), in.getSessionToken(), in.getAmount());
            response = ResponseEntity.ok(out);

        } catch (InvalidSessionException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            response = ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Status(e.getErrorCode()));
        } catch (InvalidAmountException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Status(e.getErrorCode()));
        }

        logger.log(Level.FINE,"refund(" + in + ") return " + response.getBody());
        return response;
    }
}
